package com.xy.amsd.core.auth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy.amsd.core.auth.Role;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AuthUtils {

    public static boolean check(HttpServletRequest request) {
        // 从请求头中提取用户具有的权限UserRoles
        String rolesHeader = request.getHeader("roles");
        Set<String> userRoles = getRoleSetFromJSON(rolesHeader);
        if (userRoles == null) return false;

        String callMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String callClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        // System.out.println(callClassName+'.'+callMethodName);
        // 从注解中提取需要的权限列表requiredRoles
        Role role = null;
        try {
            Method method = null;
            for (Method m : Class.forName(callClassName).getMethods()) {
                if (m.getName().equals(callMethodName)) {
                    method = m;
                    break;
                }
            }
            //Method method = Class.forName(callClassName).getMethod(callMethodName);
            role = method.getAnnotation(Role.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (role == null || role.value() == null) {
            return false;
        }
        String[] requiredRoles = role.value();
        return userRoles.containsAll(Arrays.asList(requiredRoles));
    }

    public static boolean check(HttpServletRequest request, String requiredRole) {
        String rolesHeader = request.getHeader("roles");
        Set<String> roles = null;
        roles = getRoleSetFromJSON(rolesHeader);
        if (roles == null) return false;
        return roles.contains(requiredRole);
    }

    public static Set<String> getRoleSetFromJSON(String jsonString) {
        // 调试用
        /*
        String callMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String callClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        System.out.println(callClassName+'.'+callMethodName);
        Role requiredRole = null;
        try {
            Method method = Class.forName(callClassName).getDeclaredMethod(callMethodName);
            requiredRole = method.getAnnotation(Role.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] values = requiredRole.value();
        System.out.println(Arrays.toString(values));*/

        Set<String> roles = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            roles = objectMapper.readValue(jsonString, Set.class);
            /*
            for (String rolee : roles) {
                System.out.print(rolee + '\t');
            }
            System.out.println();*/
        } catch (Exception e) {
            return null;
        }
        return roles;
    }

    @Role(value = "hello")
    private static void caller() {
        String json = "[\"sys:user:read\",\"sys:user:write\",\"sys:user:delete\"]";
        AuthUtils.getRoleSetFromJSON(json);
    }


    public static void main(String[] args) {
        caller();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> list = new ArrayList<>();
        list.add("sys:user:view");
        list.add("sys:user:add");
        list.add("sys:user:edit");
        try {
            System.out.println(objectMapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
