package scau.pdc.demo.security.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import scau.pdc.demo.security.controller.request.api.LoginReq;

public interface AuthService {

    void pageLogin(@NonNull String username, @NonNull String password, @NonNull HttpServletResponse response) throws Exception;

    void pageLogout(@NonNull HttpServletResponse response);

    @NonNull
    String apiLogin(@NonNull LoginReq req);
}
