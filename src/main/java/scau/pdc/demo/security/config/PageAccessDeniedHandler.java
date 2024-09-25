package scau.pdc.demo.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
public class PageAccessDeniedHandler implements AccessDeniedHandler {

    private final static String KEY_ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_PAGE = "/error/403";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 如果响应已经提交，则不执行后续操作
        if (response.isCommitted()) {
            log.trace("Did not write to response since already committed");
            return;
        }

        // 将认证异常信息设置到请求属性中
        request.setAttribute(KEY_ERROR_MESSAGE, accessDeniedException);
        // 设置响应状态码为403
        response.setStatus(HttpStatus.FORBIDDEN.value());

        log.info("Forwarding to {} with status code 403", ERROR_PAGE);
        // 转发请求到错误页面
        request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
    }
}
