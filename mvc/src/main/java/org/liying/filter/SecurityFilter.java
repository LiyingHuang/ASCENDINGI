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
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 1. extract authorization header
        // 2. remove Bearer to get token
        // 3. decrypt token to get claim
        // 4. verify username information in our db from claim
        // 5. doFilter dispatch to controller
        if (userService == null) {
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletRequest.getServletContext());
        }
        int statusCode = authorization((HttpServletRequest)servletRequest);
        logger.info("Security Filter");
        if(statusCode == HttpServletResponse.SC_ACCEPTED)filterChain.doFilter(servletRequest,servletResponse);
        else ((HttpServletResponse)servletResponse).sendError(statusCode);
    }

    @Override
    public void destroy() {

    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        String verb = req.getMethod();
        if (uri.equalsIgnoreCase(AUTH_URI) || uri.equalsIgnoreCase(SIGNUP_URI)) return HttpServletResponse.SC_ACCEPTED;
        try {
            String token = req.getHeader("Authorization").replaceAll("^(.*?) ", ""); // 去掉 Beare:<>    ^ 匹配行首
            if (token == null || token.isEmpty()) return statusCode;
            // decrypt token to get claims
            Claims claims = jwtService.decryptJwtToken(token);
            // check claims with the first and second part of JWT
            if(claims.getId()!=null){
                User u = userService.findById(Long.valueOf(claims.getId()));
                if(u==null) return statusCode;
//                statusCode = HttpServletResponse.SC_ACCEPTED;
            }
            String allowedResources = "/";
            switch(verb) {
                case "GET"     : allowedResources = (String)claims.get("allowedReadResources");   break;
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
