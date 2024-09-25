package scau.pdc.demo.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import scau.pdc.demo.security.entity.User;
import scau.pdc.demo.security.repository.UserRepository;
import scau.pdc.demo.security.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @NonNull
    @Override
    public User findUserById(@NonNull Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found by id: {}", id);
            return new Exception("User not found");
        });
    }

    @NonNull
    @Override
    public User saveBlogger(@NonNull User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoleName("BLOGGER");
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllBloggers() {
        return userRepository.findByRoleName("BLOGGER");
    }

    @Override
    public void deleteBlogger(@NonNull Long id) throws Exception {
        int result = userRepository.deleteByIdAndRoleName(id, "BLOGGER");
        if (result == 0) {
            throw new Exception("User to be deleted not found");
        }
    }

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername called");
        return userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("User not found by username: {}", username);
            return new UsernameNotFoundException("User not found");
        });
    }
}
