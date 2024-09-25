package scau.pdc.demo.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import scau.pdc.demo.security.controller.request.api.BloggerCreateReq;
import scau.pdc.demo.security.entity.Article;
import scau.pdc.demo.security.entity.User;
import scau.pdc.demo.security.service.ArticleService;
import scau.pdc.demo.security.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    private final UserService userService;
    private final ArticleService articleService;

    @PostMapping("/blogger/create")
    public ApiResponse<User> bloggerCreate(@RequestBody BloggerCreateReq req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        return ApiResponse.success(userService.saveBlogger(user));
    }

    @GetMapping("/blogger/list")
    public ApiResponse<List<User>> bloggerList() {
        return ApiResponse.success(userService.findAllBloggers());
    }

    @PostMapping("/blogger/delete")
    public ApiResponse<Void> bloggerDelete(@RequestParam String id) throws Exception {
        userService.deleteBlogger(Long.parseLong(id));
        return ApiResponse.success(null);
    }

    @GetMapping("/article/list")
    public ApiResponse<List<Article>> articleList() {
        return ApiResponse.success(articleService.findAllArticles());
    }

    @PostMapping("/article/delete")
    public ApiResponse<Void> articleDelete(@RequestParam String id) {
        articleService.deleteArticle(Long.parseLong(id));
        return ApiResponse.success(null);
    }

}
