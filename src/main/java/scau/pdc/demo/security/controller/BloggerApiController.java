package scau.pdc.demo.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import scau.pdc.demo.security.controller.request.api.ArticlePostOrUpdateReq;
import scau.pdc.demo.security.entity.Article;
import scau.pdc.demo.security.service.ArticleService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blogger")
public class BloggerApiController {

    private final ArticleService articleService;

    @PostMapping("/article/post")
    public ApiResponse<Article> articlePost(@RequestBody ArticlePostOrUpdateReq req) {
        Article article = new Article();
        article.setTitle(req.getTitle());
        article.setContent(req.getContent());
        article = articleService.saveArticleOfCurrentUser(article);
        return ApiResponse.success(article);
    }

    @GetMapping("/article/list")
    public ApiResponse<List<Article>> articleList() {
        List<Article> articles = articleService.findArticlesOfCurrentUser();
        return ApiResponse.success(articles);
    }

    @PostMapping("/article/update")
    public ApiResponse<Void> articleUpdate(@RequestBody ArticlePostOrUpdateReq req) {
        Article article = new Article();
        article.setId(req.getId());
        article.setTitle(req.getTitle());
        article.setContent(req.getContent());
        articleService.updateArticleOfCurrentUser(article);
        return ApiResponse.success(null);
    }

    @PostMapping("/article/delete")
    public ApiResponse<Void> articleDelete(@RequestParam String id) throws Exception {
        articleService.deleteArticleOfCurrentUser(Long.parseLong(id));
        return ApiResponse.success(null);
    }

}
