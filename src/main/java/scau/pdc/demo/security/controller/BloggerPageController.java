package scau.pdc.demo.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scau.pdc.demo.security.entity.Article;
import scau.pdc.demo.security.service.ArticleService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/blogger")
public class BloggerPageController {

    private final ArticleService articleService;

    @RequestMapping("/article/post")
    public String articlePost(Model model) {
        return "blogger-article-post";
    }

    @RequestMapping("/article/doPost")
    public String articleDoPost(@RequestParam String title,
                                @RequestParam String content) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        articleService.saveArticleOfCurrentUser(article);
        return "redirect:/blogger/article/list";
    }

    @RequestMapping("/article/list")
    public String articleList(Model model) {
        model.addAttribute("articles", articleService.findArticlesOfCurrentUser());
        return "blogger-article-list";
    }

    @RequestMapping("/article/update")
    public String articleUpdate(@RequestParam String id, Model model) throws Exception {
        Article article = articleService.findArticleByIdOfCurrentUser(Long.parseLong(id));
        model.addAttribute("article", article);
        return "blogger-article-update";
    }

    @RequestMapping("/article/doUpdate")
    public String articleDoUpdate(@RequestParam String id,
                                  @RequestParam String title,
                                  @RequestParam String content) {
        Article article = new Article();
        article.setId(Long.parseLong(id));
        article.setTitle(title);
        article.setContent(content);
        articleService.updateArticleOfCurrentUser(article);
        return "redirect:/blogger/article/list";
    }

    @RequestMapping("/article/doDelete")
    public String articleDoDelete(@RequestParam String id) throws Exception {
        articleService.deleteArticleOfCurrentUser(Long.parseLong(id));
        return "redirect:/blogger/article/list";
    }

}
