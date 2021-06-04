package com.xy.ams.admin.util;

import com.xy.ams.admin.security.GrantedAuthorityImpl;
import com.xy.ams.admin.security.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String USERNAME = Claims.SUBJECT;

    private static final String CREATED = "created";

    private static final String AUTHORITIES = "authorities";

    private static final String SECRET = "abcdefgh";

    private static final long EXPRIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * 使用认证信息生成token
     * (将用户名，创建时间和权限添加到JWT token的payload部分)
     * @param authentication 登录认证信息
     * @return token
     */
    public static String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(USERNAME, SecurityUtils.getUserName(authentication));
        claims.put(CREATED, new Date());
        claims.put(AUTHORITIES, authentication.getAuthorities());
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPRIRE_TIME);
        return Jwts.builder().setClaims(claims).setExpiration(expireDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 从HttpRequest中获取token，然后提取其登录认证信息（Authentication对象）；
     * @param request HttpServletRequest
     * @return 登录认证信息Authentication
     */
    public static Authentication getAuthenticationFromRequest(HttpServletRequest request) {
        Authentication authentication = null;

        String token = JwtTokenUtils.getToken(request);
        if (token != null) {
            // 如果security上下文中没有authentication对象
            if (SecurityUtils.getAuthentication() == null) {
                Claims claims = getClaimsFromToken(token);
                if (claims == null) return null;
                String userName = claims.getSubject();
                if (userName == null) return null;
                if (isTokenExpired(token)) return null;

                Object claimAuthorities = claims.get(AUTHORITIES);
                // 从claims中取出权限列表，并转换为GrantedAuthority列表
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (claimAuthorities != null && claimAuthorities instanceof List) {
                    for (Object object : (List) claimAuthorities) {
                        // TODO 这里将(String)强制转换 改成了toString();
                        String authority = ((Map) object).get("authority").toString();
                        authorities.add(new GrantedAuthorityImpl(authority));
                    }
                }
                authentication = new JwtAuthenticationToken(userName, null, authorities, token);
            } else {
                // 如果请求中的token合法，直接从上下文中取出authentication对象并返回
                if (isTokenValid(token, SecurityUtils.getUserName())) {
                    authentication = SecurityUtils.getAuthentication();
                }
            }
        }
        return authentication;
    }

    public static String getUserNameFromToken(String token) {
        String userName;
        try {
            Claims claims = getClaimsFromToken(token);
            userName = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            userName = null;
        }
        return userName;
    }

    /**
     * 从token中取出claims数据
     * @param token token
     * @return claims
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }

    /**
     * 判断给定的token是否合法
     * （用户名相同且未过期）
     * @param token token
     * @param userName 用户名
     * @return ( )
     */
    public static Boolean isTokenValid(String token, String userName) {
        String userNameFromToken = getUserNameFromToken(token);
        // token中的用户名与给定用户名相同，且未过期
        return userNameFromToken.equals(userName) && !isTokenExpired(token);

    }

    /**
     * 判断给定的token是否已经过期
     * @param token token
     * @return ( )
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expireDate = claims.getExpiration();
            return expireDate.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从HttpRequest中取出token
     * @param request HttpRequest
     * @return token
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        // JWT格式的规定
        String tokenHead = "Bearer ";
        if (token == null) {
            token = request.getHeader("token");
        } else if (token.contains(tokenHead)) {
            token = token.substring(tokenHead.length());
        }
        if ("".equals(token)) token = null;
        return token;
    }
}
