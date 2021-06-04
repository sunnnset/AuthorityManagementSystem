package com.xy.amsd.core.auth.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy.amsd.core.auth.util.AuthUtils;
import com.xy.amsd.core.auth.Role;
import com.xy.amsd.core.http.HttpResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 前置拦截器，判断方法声明的权限与http请求的权限是否符合
     * @param request request
     * @param response response
     * @param handler handler
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从调用的controller方法中提取方法需要的权限
        List<String> requiredRoles = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Role annotationRole = handlerMethod.getMethodAnnotation(Role.class);
            if (annotationRole == null || annotationRole.value() == null) {
                // 如果没有使用权限注解，直接放行
                return true;
            }
            requiredRoles = Arrays.asList(annotationRole.value());
        }
        if (requiredRoles == null) return true;
        // 从请求头中提取用户的权限
        Set<String> userRoles = AuthUtils.getRoleSetFromJSON(request.getHeader("roles"));
        // 判断权限是否满足
        if (userRoles == null || !userRoles.containsAll(requiredRoles)) {
            writeFailure(response);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 权限不满足时，设置错误response
     * @param response HttpServletResponse
     * @throws IOException
     */
    private void writeFailure(HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpResult httpResult = new HttpResult(403, "Forbidden");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(httpResult));
        writer.flush();
        writer.close();
    }


}
