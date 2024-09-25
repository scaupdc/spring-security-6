package scau.pdc.demo.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import scau.pdc.demo.security.entity.User;
import scau.pdc.demo.security.repository.UserRepository;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TestApplication.class, args);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername("scau_pdc2");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRoleName("ADMIN");
        UserRepository repository = context.getBean(UserRepository.class);
        repository.save(user);
    }
}
