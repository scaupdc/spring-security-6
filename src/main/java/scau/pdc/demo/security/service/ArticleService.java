package scau.pdc.demo.security.service;

import org.springframework.lang.NonNull;
import scau.pdc.demo.security.entity.Article;

import java.util.List;

public interface ArticleService {

    @NonNull List<Article> findAllArticles();

    @NonNull Article findArticleById(@NonNull Long id) throws Exception;

    @NonNull Article saveArticleOfCurrentUser(@NonNull Article article);

    @NonNull List<Article> findArticlesOfCurrentUser();

    @NonNull Article findArticleByIdOfCurrentUser(@NonNull Long id) throws Exception;

    void updateArticleOfCurrentUser(@NonNull Article article);

    void deleteArticleOfCurrentUser(@NonNull Long id) throws Exception;

    void deleteArticle(@NonNull Long id);
}
