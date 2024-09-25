package scau.pdc.demo.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scau.pdc.demo.security.entity.User;
import scau.pdc.demo.security.service.ArticleService;
import scau.pdc.demo.security.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final UserService userService;
    private final ArticleService articleService;

    @RequestMapping("/blogger/create")
    public String bloggerCreate(Model model) {
        return "admin-blogger-create";
    }

    @RequestMapping("/blogger/doCreate")
    public String bloggerDoCreate(@RequestParam String username,
                                  @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.saveBlogger(user);
        return "redirect:/admin/blogger/list";
    }

    @RequestMapping("/blogger/list")
    public String bloggerList(Model model) {
        List<User> bloggers = userService.findAllBloggers();
        model.addAttribute("bloggers", bloggers);
        return "admin-blogger-list";
    }

    @RequestMapping("/blogger/doDelete")
    public String bloggerDoDelete(@RequestParam String id) throws Exception {
        userService.deleteBlogger(Long.parseLong(id));
        return "redirect:/admin/blogger/list";
    }

    @RequestMapping("/article/list")
    public String articleList(Model model) {
        model.addAttribute("articles", articleService.findAllArticles());
        return "admin-article-list";
    }

    @RequestMapping("/article/doDelete")
    public String articleDoDelete(@RequestParam String id) {
        articleService.deleteArticle(Long.parseLong(id));
        return "redirect:/admin/article/list";
    }

}
