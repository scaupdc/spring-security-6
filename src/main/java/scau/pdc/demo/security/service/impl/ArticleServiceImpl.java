package scau.pdc.demo.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import scau.pdc.demo.security.entity.Article;
import scau.pdc.demo.security.entity.User;
import scau.pdc.demo.security.repository.ArticleRepository;
import scau.pdc.demo.security.service.ArticleService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @NonNull
    @Override
    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    @NonNull
    @Override
    public Article findArticleById(@NonNull Long id) throws Exception {
        return articleRepository.findById(id).orElseThrow(() -> {
            log.error("Article not found by id: {}", id);
            return new Exception("Article not found");
        });
    }

    @NonNull
    @Override
    public Article saveArticleOfCurrentUser(@NonNull Article article) {
        User currentUser = User.currentUser();
        article.setUserId(currentUser.getId());
        article.setPostDate(new Date());
        return articleRepository.save(article);
    }

    @NonNull
    @Override
    public List<Article> findArticlesOfCurrentUser() {
        User currentUser = User.currentUser();
        return articleRepository.findByUserId(currentUser.getId());
    }

    @NonNull
    @Override
    public Article findArticleByIdOfCurrentUser(@NonNull Long id) throws Exception {
        User currentUser = User.currentUser();
        return articleRepository.findByIdAndUserId(id, currentUser.getId()).orElseThrow(() -> {
            log.error("Article not found by id: {} and userId: {}", id, currentUser.getId());
            return new Exception("Article not found");
        });
    }

    @Override
    public void updateArticleOfCurrentUser(@NonNull Article article) {
        User currentUser = User.currentUser();
        article.setUserId(currentUser.getId());
        articleRepository.update(article);
    }

    @Override
    public void deleteArticleOfCurrentUser(@NonNull Long id) throws Exception {
        User currentUser = User.currentUser();
        int result = articleRepository.deleteByIdAndUserId(id, currentUser.getId());
        if (result == 0) {
            throw new Exception("Article to be deleted not found");
        }
    }

    @Override
    public void deleteArticle(@NonNull Long id) {
        int result = articleRepository.deleteById(id);
        if (result == 0) {
            throw new RuntimeException("Article to be deleted not found");
        }
    }
}
