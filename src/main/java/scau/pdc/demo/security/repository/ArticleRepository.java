package scau.pdc.demo.security.repository;

import org.springframework.lang.NonNull;
import scau.pdc.demo.security.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    @NonNull
    List<Article> findAll();

    Optional<Article> findById(@NonNull Long id);

    @NonNull
    Article save(@NonNull Article article);

    @NonNull
    List<Article> findByUserId(@NonNull Long userId);

    Optional<Article> findByIdAndUserId(@NonNull Long id, @NonNull Long userId);

    int update(@NonNull Article article);

    int deleteByIdAndUserId(@NonNull Long id, @NonNull Long userId);

    int deleteById(@NonNull Long id);
}
