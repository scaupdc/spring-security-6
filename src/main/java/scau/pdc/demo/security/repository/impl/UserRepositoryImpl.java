package scau.pdc.demo.security.repository.impl;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import scau.pdc.demo.security.entity.User;
import scau.pdc.demo.security.repository.BaseRepository;
import scau.pdc.demo.security.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends BaseRepository implements UserRepository {

    @Override
    public Optional<User> findById(@NonNull Long id) {
        String sql = """
                SELECT * FROM t_user tu
                WHERE tu.id = :id
                """;
        return jdbcClient.sql(sql).param("id", id).query(User.class).optional();
    }

    @Override
    public Optional<User> findByUsername(@NonNull String username) {
        String sql = """
                SELECT * FROM t_user tu
                WHERE tu.username = :username
                """;
        return jdbcClient.sql(sql).param("username", username).query(User.class).optional();
    }

    @NonNull
    @Override
    public User save(@NonNull User user) {
        String sql = """
                INSERT INTO t_user (username, password, role_name)
                VALUES (:username, :password, :roleName)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("roleName", user.getRoleName())
                .update(keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public List<User> findByRoleName(@NonNull String roleName) {
        String sql = """
                SELECT * FROM t_user
                WHERE role_name = :roleName
                """;
        return jdbcClient.sql(sql)
                .param("roleName", roleName)
                .query(User.class).list();
    }

    @Override
    public int deleteByIdAndRoleName(@NonNull Long id, @NonNull String roleName) {
        String sql = """
                DELETE FROM t_user
                WHERE id = :id AND role_name = :roleName
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .param("roleName", roleName)
                .update();
    }
}
