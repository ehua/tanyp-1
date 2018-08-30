package me.tanyp.util;

import me.tanyp.controller.common.SystemException;
import me.tanyp.util.basic.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by tanyp on 2018/8/22
 */
public class SendMail {

    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private Logger logger = Logger.getLogger(SendMail.class);

    public void sendMail(String email) {
        if (StringUtils.isNotEmpty(email) && Pattern.matches(REGEX_EMAIL, email)) {
            try {
                MailConfig mail = SpringServiceFactory.get().getService(MailConfig.class);
                Properties properties = new Properties();
                properties.setProperty("mail.smtp.auth", mail.getAuth());
                properties.setProperty("mail.transport.protocol", mail.getProtocol());
                properties.setProperty("mail.smtp.host", mail.getHost());
                Authenticator authenticator = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mail.getName(), mail.getPassword());
                    }
                };
                Session session = Session.getInstance(properties, authenticator);
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(MimeUtility.encodeText("YhACG") + "<" + mail.getName() + ">"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject(MimeUtility.encodeText("验证码"));
                addSendMailTask(message, email, Integer.parseInt(mail.getTimeout()));
            } catch (Exception e) {
                throw new SystemException("邮件发送异常", e);
            }
        } else {
            throw new SystemException("邮箱不正确{" + email + "}");
        }
    }

    private void addSendMailTask(final Message message, final String email, final int timeOut) {
        try {
            ThreadPoolTaskExecutor taskExecutor = SpringServiceFactory.get()
                    .getService(ThreadPoolTaskExecutor.class);
            int code = (int) ((Math.random() * 9 + 1) * 10000);
            message.setContent(String.valueOf(code), "text/html;charset=utf-8");
            final RedisManager redisManager = SpringServiceFactory.get()
                    .getService(RedisManager.class);
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(message);
                        redisManager.put(email, code);
                        redisManager.expire(email, timeOut);
                    } catch (Exception e) {
                        logger.error("邮件发送异常,邮件详细信息为{}", e);
                    }
                }
            });
        } catch (MessagingException e) {
            logger.error("邮件操作异常", e);
        }
    }
}
