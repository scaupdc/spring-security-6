package scau.pdc.demo.security.controller.response.api;

import lombok.Data;
import scau.pdc.demo.security.entity.Article;

import java.util.Date;

@Data
public class ArticleDetailResp {

    private Long id;
    private String title;
    private String content;
    private Date postDate;
    private Long userId;
    private String username;

    public static ArticleDetailResp of(Article article, String username) {
        ArticleDetailResp resp = new ArticleDetailResp();
        resp.setId(article.getId());
        resp.setTitle(article.getTitle());
        resp.setContent(article.getContent());
        resp.setPostDate(article.getPostDate());
        resp.setUserId(article.getUserId());
        resp.setUsername(username);
        return resp;
    }

}
