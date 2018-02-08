package com.tanyouping.weixiao.interceptor;

import com.tanyouping.weixiao.security.UserManager;
import com.tanyouping.weixiao.util.StringUtils;
import com.tanyouping.weixiao.web.client.ClientInfo;
import com.tanyouping.weixiao.web.client.ClientInfoHolder;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Tan Youping on 2018/1/9
 */
public class GlobalHandleInterceptor implements HandlerInterceptor{

    @Autowired
    private UserManager userManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        userManager.putSessionId(request.getSession().getId());
        ClientInfo clientInfo = ClientInfoHolder.get();
        UserAgent ua = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        clientInfo.setBrowser(ua.getBrowser().getName());
        clientInfo.setIp(getRealIp(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private String getRealIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
