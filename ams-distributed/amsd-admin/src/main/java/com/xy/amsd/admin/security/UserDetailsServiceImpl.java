package com.xy.amsd.admin.security;

import com.xy.amsd.admin.model.SysUser;
import com.xy.amsd.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 提供用户登录认证信息的查询
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户名返回对应的JwtUserDetails对象，包括用户名、密码、 盐和用户角色列表。
     * @param username 用户名
     * @return JwtUserDetails对象
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Set<String> permissions = sysUserService.findUserPermissions(user.getName());
        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
        return new JwtUserDetails(user.getName(), user.getPassword(), user.getSalt(), grantedAuthorities);
    }
}
