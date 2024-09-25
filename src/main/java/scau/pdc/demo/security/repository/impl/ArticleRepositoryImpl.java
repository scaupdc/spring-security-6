package scau.pdc.demo.security.repository.impl;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import scau.pdc.demo.security.entity.Article;
import scau.pdc.demo.security.repository.ArticleRepository;
import scau.pdc.demo.security.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl extends BaseRepository implements ArticleRepository {

    @NonNull
    @Override
    public List<Article> findAll() {
        String sql = """
                SELECT * FROM t_article
                """;
        return jdbcClient.sql(sql).query(Article.class).list();
    }

    @Override
    public Optional<Article> findById(@NonNull Long id) {
        String sql = """
                SELECT * FROM t_article ta
                WHERE ta.id = :id
                """;
        return jdbcClient.sql(sql).param("id", id).query(Article.class).optional();
    }

    @NonNull
    @Override
    public Article save(@NonNull Article article) {
        String sql = """
                INSERT INTO t_article (title, content, post_date, user_id)
                VALUES (:title, :content, :postDate, :userId)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .param("title", article.getTitle())
                .param("content", article.getContent())
                .param("postDate", article.getPostDate())
                .param("userId", article.getUserId())
                .update(keyHolder);
        article.setId(keyHolder.getKey().longValue());
        return article;
    }

    @NonNull
    @Override
    public List<Article> findByUserId(@NonNull Long userId) {
        String sql = """
                SELECT * FROM t_article ta
                WHERE ta.user_id = :userId
                """;
        return jdbcClient.sql(sql).param("userId", userId).query(Article.class).list();
    }

    @Override
    public Optional<Article> findByIdAndUserId(@NonNull Long id, @NonNull Long userId) {
        String sql = """
                SELECT * FROM t_article ta
                WHERE ta.id = :id
                AND ta.user_id = :userId
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .param("userId", userId)
                .query(Article.class).optional();
    }

    @Override
    public int update(@NonNull Article article) {
        String sql = """
                UPDATE t_article
                SET title = :title, content = :content
                WHERE id = :id AND user_id = :userId
                """;
        return jdbcClient.sql(sql)
                .param("title", article.getTitle())
                .param("content", article.getContent())
                .param("id", article.getId())
                .param("userId", article.getUserId())
                .update();
    }

    @Override
    public int deleteByIdAndUserId(@NonNull Long id, @NonNull Long userId) {
        String sql = """
                DELETE FROM t_article
                WHERE id = :id AND user_id = :userId
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .param("userId", userId)
                .update();
    }

    @Override
    public int deleteById(@NonNull Long id) {
        String sql = """
                DELETE FROM t_article
                WHERE id = :id
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .update();
    }
}
