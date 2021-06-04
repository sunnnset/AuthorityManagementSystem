package com.xy.ams.admin.util;

import com.xy.ams.admin.security.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    /**
     * 用于登录验证成功后生成token
     * @param request Http请求
     * @param userName 用户名
     * @param password 密码
     * @param authenticationManager ？？？
     * @return
     */
    public static JwtAuthenticationToken login(HttpServletRequest request, String userName, String password, AuthenticationManager authenticationManager) {
        JwtAuthenticationToken token = new JwtAuthenticationToken(userName, password);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 调用JwtTokenUtils生成token
        token.setToken(JwtTokenUtils.generateToken(authentication));
        return token;
    }

    /**
     * 从HTTP request中提取token，验证token的合法性
     * 如果合法，将认证信息加载到security的上下文中
     * @param request HTTP request对象
     */
    public static void checkAuthentication(HttpServletRequest request) {
        // 调用JwtTokenUtils的方法，提取token的信息
        Authentication authentication = JwtTokenUtils.getAuthenticationFromRequest(request);
        // 加载认证信息到security上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static String getUserName() {
        String userName = null;
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof UserDetails) {
                userName = ((UserDetails) principal).getUsername();
            }
        }
        return userName;
    }

    public static String getUserName(Authentication authentication) {
        String userName = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof UserDetails) {
                userName = ((UserDetails) principal).getUsername();
            }
        }
        return userName;
    }

    /**
     * 从Spring Security的上下文中取出authentication对象
     * @return
     */
    public static Authentication getAuthentication() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        } else {
            // 从Security的上下文中获取authentication对象
            return SecurityContextHolder.getContext().getAuthentication();
        }
    }

}
