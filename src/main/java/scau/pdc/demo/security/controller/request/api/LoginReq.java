package scau.pdc.demo.security.controller.request.api;

import lombok.Data;

@Data
public class LoginReq {

    private String username;
    private String password;

}
