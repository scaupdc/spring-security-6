package scau.pdc.demo.security.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;

public class BaseRepository {

    @Autowired
    protected JdbcClient jdbcClient;

}
