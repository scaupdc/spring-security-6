package scau.pdc.demo.security.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scau.pdc.demo.security.controller.request.api.LoginReq;
import scau.pdc.demo.security.entity.User;
import scau.pdc.demo.security.service.AuthService;
import scau.pdc.demo.security.util.JwtUtil;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private static final String COOKIE_NAME = "access_token";
    private static final String COOKIE_PATH = "/";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24;

    @Override
    public void pageLogin(@NonNull String username, @NonNull String password, @NonNull HttpServletResponse response) throws Exception {
        // 创建一个用户名密码认证令牌
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 使用认证管理器进行认证
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);
        // 将认证结果设置到安全上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        // 从认证结果中获取用户信息
        User user = (User) authenticated.getPrincipal();
        // 使用用户的用户名生成JWT令牌
        String token = JwtUtil.generateJwtToken(user.getUsername());
        // 创建一个Cookie对象，用于存储JWT令牌
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        // 设置Cookie的HttpOnly属性为true，防止通过客户端脚本访问
        cookie.setHttpOnly(true);
        // 设置Cookie的路径
        cookie.setPath(COOKIE_PATH);
        // 设置Cookie的最大生存时间
        cookie.setMaxAge(COOKIE_MAX_AGE);
        // 将Cookie添加到响应中
        response.addCookie(cookie);
    }

    @Override
    public void pageLogout(@NonNull HttpServletResponse response) {
        // 清除Cookie
        response.addCookie(new Cookie(COOKIE_NAME, null));
    }

    @NonNull
    @Override
    public String apiLogin(@NonNull LoginReq req) {
        // 创建一个用户名密码认证令牌
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        // 使用认证管理器进行认证
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);
        // 将认证结果设置到安全上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        // 从认证结果中获取用户信息
        User user = (User) authenticated.getPrincipal();
        // 使用用户的用户名生成JWT令牌
        return JwtUtil.generateJwtToken(user.getUsername());
    }

}
