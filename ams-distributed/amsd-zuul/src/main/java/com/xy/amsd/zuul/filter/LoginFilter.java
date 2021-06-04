package com.xy.amsd.zuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        ObjectMapper mapper = new ObjectMapper();

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        System.out.println(
                "Pre Filter: Request Method : " + request.getMethod() + " " +
                        "Request URL : " + request.getRequestURL().toString());

        /*
        List<String> roles = new ArrayList<>();
        roles.add("sys:user:fuck");

        String jsonHeader = null;
        try {
            jsonHeader = mapper.writeValueAsString(roles);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ctx.addZuulRequestHeader("roles", jsonHeader);*/

        return null;
    }
}
