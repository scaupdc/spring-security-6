package scau.pdc.demo.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 如果响应已经提交，则不执行后续操作
        if (response.isCommitted()) {
            log.trace("Did not write to response since already committed");
            return;
        }

        log.info("Response with status code 403");

        // 设置响应状态码为403
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // 设置响应内容类型为json
        response.setContentType("application/json");
        // 设置响应内容编码为utf-8
        response.setCharacterEncoding("UTF-8");
        // 创建JSON字符串并写入响应输出流中
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", HttpStatus.FORBIDDEN.value());
        responseMap.put("message", accessDeniedException.getMessage());
        responseMap.put("data", null);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), responseMap);
    }
}
