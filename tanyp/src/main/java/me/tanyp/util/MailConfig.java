package me.tanyp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by tanyp on 2018/8/22
 */
@Component
public class MailConfig {

    private String auth;
    private String protocol;
    private String host;
    private String name;
    private String password;
    private String subject;
    private String timeout;

    public String getAuth() {
        return auth;
    }
    @Value("${mail.smtp.auth}")
    public void setAuth(String auth) {
        this.auth = auth;
    }
    public String getProtocol() {
        return protocol;
    }
    @Value("${mail.transport.protocol}")
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }
    @Value("${mail.smtp.host}")
    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }
    @Value("${mail.user.name}")
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    @Value("${mail.user.password}")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }
    @Value("${mail.subject}")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimeout() {
        return timeout;
    }
    @Value("${mail.timeout}")
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
