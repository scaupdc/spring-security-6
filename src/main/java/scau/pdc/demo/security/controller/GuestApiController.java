package scau.pdc.demo.security.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import scau.pdc.demo.security.controller.request.api.LoginReq;
import scau.pdc.demo.security.controller.response.api.ArticleDetailResp;
import scau.pdc.demo.security.entity.Article;
import scau.pdc.demo.security.service.ArticleService;
import scau.pdc.demo.security.service.AuthService;
import scau.pdc.demo.security.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GuestApiController {

    private final ArticleService articleService;
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/article/list")
    public ApiResponse<List<Article>> articleList() {
        List<Article> articles = articleService.findAllArticles();
        return ApiResponse.success(articles);
    }

    @GetMapping("/article/detail")
    public ApiResponse<ArticleDetailResp> articleDetail(@RequestParam Long id) throws Exception {
        Article article = articleService.findArticleById(id);
        String username = userService.findUserById(article.getUserId()).getUsername();
        ArticleDetailResp resp = ArticleDetailResp.of(article, username);
        return ApiResponse.success(resp);
    }

    @PostMapping("/auth/login")
    public ApiResponse<String> authLogin(@RequestBody LoginReq req) throws Exception {
        log.info("username: {}, password: {}", req.getUsername(), req.getPassword());
        String token = authService.apiLogin(req);
        return ApiResponse.success(token);
    }

    @PostMapping("/auth/logout")
    public ApiResponse<Void> authLogout(RedirectAttributes redirectAttributes,
                                        HttpServletResponse response) throws Exception {
        log.info("logout");
        return ApiResponse.success(null);
    }

}
