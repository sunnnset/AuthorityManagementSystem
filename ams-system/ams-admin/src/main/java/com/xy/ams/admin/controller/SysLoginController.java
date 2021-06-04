package com.xy.ams.admin.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.xy.ams.admin.model.SysUser;
import com.xy.ams.admin.security.JwtAuthenticationToken;
import com.xy.ams.admin.service.SysUserService;
import com.xy.ams.admin.util.PasswordUtils;
import com.xy.ams.admin.util.SecurityUtils;
import com.xy.ams.admin.vo.LoginBean;
import com.xy.ams.core.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping(value = "login")
public class SysLoginController {

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = "captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成验证码文字和图片，并将文字放入session中
        String text = captchaProducer.createText();
        BufferedImage image = captchaProducer.createImage(text);
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        //
        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "jpg", out);
        }
    }

    @PostMapping(value = "login")
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
        String loginUserName = loginBean.getUserName();
        String loginPassword = loginBean.getPassword();
        String loginCaptcha = loginBean.getCaptcha();
        String captcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (captcha == null) return HttpResult.error("验证码已失效");
        if (!captcha.equals(loginCaptcha)) return HttpResult.error("验证码错误");
        // 检查用户名与密码是否正确
        SysUser user = sysUserService.findByName(loginUserName);
        if (user == null) return HttpResult.error("用户名不存在");
        if (!PasswordUtils.matches(user.getSalt(), user.getPassword(), loginPassword)) {
            return HttpResult.error("密码错误");
        }
        if (user.getStatus() == 0) return HttpResult.error("账号已被锁定，请联系管理员");
        // 生成token，存入session中，并返回给用户
        JwtAuthenticationToken token = SecurityUtils.login(request, loginUserName, loginPassword, authenticationManager);
        // TODO: 是否应该将完整的JwtAuthenticationToken对象返回给用户？
        return HttpResult.ok(token);
    }
}
