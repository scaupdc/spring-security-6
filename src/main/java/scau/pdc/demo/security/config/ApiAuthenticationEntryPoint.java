package scau.pdc.demo.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 如果响应已经提交，则不执行后续操作
        if (response.isCommitted()) {
            log.trace("Did not write to response since already committed");
            return;
        }

        log.info("Response with status code 401");

        // 设置响应状态码为401
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 设置响应内容类型为json
        response.setContentType("application/json");
        // 设置响应内容编码为utf-8
        response.setCharacterEncoding("UTF-8");
        // 创建JSON字符串并写入响应输出流中
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", HttpStatus.UNAUTHORIZED.value());
        responseMap.put("message", authException.getMessage());
        responseMap.put("data", null);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), responseMap);
    }

}
