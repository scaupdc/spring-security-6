package scau.pdc.demo.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import scau.pdc.demo.security.service.UserService;
import scau.pdc.demo.security.util.JwtUtil;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class PageJwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 从cookie提取并验证token
        String token = JwtUtil.getJwtTokenFromCookie(request);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        String subject = JwtUtil.getSubjectFromJwtToken(token);
        if (!StringUtils.hasText(subject)) {
            // 如果从token中无法获取subject，则直接放行
            filterChain.doFilter(request, response);
            return;
        }

        // 检查用户是否已认证
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(subject);
            if (userDetails != null && JwtUtil.verifyJwtToken(token, userDetails.getUsername())) {
                // 创建并设置认证信息
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }

}
