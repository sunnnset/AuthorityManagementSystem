package com.xy.amsd.urmservice;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("-----------------------new http request:-------------------------");
        System.out.println("requestURI: "+request.getRequestURI());
        System.out.println("requestURL: "+request.getRequestURL());
        System.out.println("request header: "+request.getHeader("roles"));
        return true;
    }
}
