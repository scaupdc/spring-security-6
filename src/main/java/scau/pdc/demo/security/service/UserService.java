package scau.pdc.demo.security.service;

import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import scau.pdc.demo.security.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    @NonNull
    User findUserById(@NonNull Long id) throws Exception;

    @NonNull
    User saveBlogger(@NonNull User user);

    List<User> findAllBloggers();

    void deleteBlogger(@NonNull Long id) throws Exception;
}
