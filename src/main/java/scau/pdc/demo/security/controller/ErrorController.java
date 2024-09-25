package scau.pdc.demo.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/error")
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Throwable.class)
    public String exception(final Throwable throwable,
                            final HttpServletRequest request,
                            final HttpServletResponse response,
                            final Model model) {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        log.error("An error occurred:{}", errorMessage);
        if (throwable instanceof NoResourceFoundException) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        // 获取request的请求路径
        String requestUrl = request.getRequestURI();
        // 如果请求路径以"/api/"开头
        if (requestUrl.startsWith("/api/")) {
            // 设置响应内容类型为json
            response.setContentType("application/json");
            // 设置响应内容编码为utf-8
            response.setCharacterEncoding("UTF-8");
            // 创建JSON字符串并写入响应输出流中
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("code", response.getStatus());
            responseMap.put("message", errorMessage);
            responseMap.put("data", null);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.writeValue(response.getWriter(), responseMap);
            } catch (IOException e) {
                return null;
            }
            return null;
        } else {
            model.addAttribute("errorCode", response.getStatus());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }

    @RequestMapping("/401")
    public String unauthorized(Model model) {
        return "error-401";
    }

    @RequestMapping("/403")
    public String accessDenied(Model model) {
        return "error-403";
    }

}