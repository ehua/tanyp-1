package me.tanyp.interceptor;

import me.tanyp.util.ClientInfo;
import me.tanyp.util.ClientInfoHolder;
import me.tanyp.util.StringUtils;
import me.tanyp.util.UserManager;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tanyp on 2018/8/12
 */
public class GlobalHandleInterceptor implements HandlerInterceptor {

    @Autowired
    private UserManager userManager;

    public GlobalHandleInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        userManager.putSessionId(request.getSession().getId());
        ClientInfo clientInfo = ClientInfoHolder.get();
        UserAgent ua = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        clientInfo.setBrowser(ua.getBrowser().getName());
        clientInfo.setIp(getRealIp(request));
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

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
