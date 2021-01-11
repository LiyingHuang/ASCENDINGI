package org.liying.filter;

import io.jsonwebtoken.Claims;
import org.liying.model.User;
import org.liying.service.JWTService;
import org.liying.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    private static String AUTH_URI = "/auth";
    private static String SIGNUP_URI = "/auth/signup";
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    // 1. extract authorization header
    // 2. remove Bearer to get token： Bearer: token -> token

    // 3. decrypt token to get claim
    // 4. verify username information in our db from claim/payload
    // 5. doFilter dispatch to controller

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (userService == null) {
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletRequest.getServletContext());
        }
        // 1. 通过authorization方法的处理看当前的request的statusCode -> SC_UNAUTHORIZED 或者 SC_ACCEPTED
        int statusCode = authorization((HttpServletRequest)servletRequest);
        logger.info("Security Filter");
        // 2.1 statusCode的状态是SC_ACCEPTED，则用doFilter传递给下一个filter
        if(statusCode == HttpServletResponse.SC_ACCEPTED) {
            filterChain.doFilter(servletRequest,servletResponse);
        }
        // 2.2 否则返回error
        else {
            ((HttpServletResponse)servletResponse).sendError(statusCode);
        }
    }

    @Override
    public void destroy() {}

    private int authorization(HttpServletRequest req) {

        // 1. 先定义一个SC_UNAUTHORIZED statusCode为SC_UNAUTHORIZED状态
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        String verb = req.getMethod();

        // 如果是登录/注册请求，则不用经过filter  /auth authentication就是登录
        if (uri.equalsIgnoreCase(AUTH_URI) || uri.equalsIgnoreCase(SIGNUP_URI)) {
            return HttpServletResponse.SC_ACCEPTED;
        }
        try {
            // 2. Authentication正式开始
            // Bearer: token -> token    ^ 匹配行首，"^(.*?) "把空格替换为""
            String token = req.getHeader("Authorization").replaceAll("^(.*?) ", "");
            // 2.1 验证token
            if (token == null || token.isEmpty()) return statusCode;
            // 2.2 decrypt token to get claims 把token里面的东西拿来去数据库取东西
            Claims claims = jwtService.decryptJwtToken(token);
            // 能通过token里面的id取到一个user
            if(claims.getId()!=null){
                User u = userService.findById(Long.valueOf(claims.getId()));
                if(u==null) return statusCode;
            // statusCode = HttpServletResponse.SC_ACCEPTED;
            }

            // Authorization
            String allowedResources = "/";
            switch(verb) {
                case "GET"    : allowedResources = (String)claims.get("allowedReadResources"); break;
                case "POST"   : allowedResources = (String)claims.get("allowedCreateResources"); break;
                case "PUT"    : allowedResources = (String)claims.get("allowedUpdateResources"); break;
                case "DELETE" : allowedResources = (String)claims.get("allowedDeleteResources"); break;
            }
            for (String s : allowedResources.split(",")) {
                if (uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }

        }
        catch (Exception e) {
            logger.error("can't verify the token",e);
        }
        return statusCode;
    }
}
