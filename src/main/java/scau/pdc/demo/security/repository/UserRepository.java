package scau.pdc.demo.security.repository;

import org.springframework.lang.NonNull;
import scau.pdc.demo.security.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(@NonNull Long id);

    Optional<User> findByUsername(@NonNull String username);

    @NonNull
    User save(@NonNull User user);

    List<User> findByRoleName(@NonNull String roleName);

    int deleteByIdAndRoleName(@NonNull Long id, @NonNull String roleName);
}
