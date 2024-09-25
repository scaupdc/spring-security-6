package scau.pdc.demo.security.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import scau.pdc.demo.security.entity.Article;
import scau.pdc.demo.security.service.ArticleService;
import scau.pdc.demo.security.service.AuthService;
import scau.pdc.demo.security.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class GuestPageController {

    private final ArticleService articleService;
    private final UserService userService;
    private final AuthService authService;

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        List<Article> articles = articleService.findAllArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @RequestMapping("/detail")
    public String detail(@RequestParam Long id, Model model) throws Exception {
        Article article = articleService.findArticleById(id);
        String username = userService.findUserById(article.getUserId()).getUsername();
        model.addAttribute("article", article);
        model.addAttribute("username", username);
        return "detail";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            log.warn("User attempted to login with error: {}", error);
            // 注入登录报错信息到页面
            model.addAttribute("error", error);
        }
        log.info("login page");
        return "login";
    }

    @RequestMapping("/doLogin")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          RedirectAttributes redirectAttributes,
                          HttpServletResponse response) throws Exception {
        log.info("username: {}, password: {}", username, password);
        authService.pageLogin(username, password, response);
        return "redirect:/";
    }

    @RequestMapping("/doLogout")
    public String doLogout(RedirectAttributes redirectAttributes,
                           HttpServletResponse response) throws Exception {
        log.info("logout");
        authService.pageLogout(response);
        return "redirect:/";
    }

}
