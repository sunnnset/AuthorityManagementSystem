package com.xy.amsd.admin.security;

import com.xy.amsd.admin.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截器，从HTTP请求中提取token
 *
 * 继承了Spring Security提供的拦截器，重写doFilterInternal方法
 * 在SecurityUtils中自定义了token的提取步骤
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 拦截（配置中规定的）所有HTTP请求，验证请求中的token是否合法，并在上下文中设置authentication对象
     * @param request HTTP request
     * @param response HTTP response
     * @param chain 拦截链
     * @throws IOException （）
     * @throws ServletException （）
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityUtils.checkAuthentication(request);
        chain.doFilter(request, response);
    }
}
