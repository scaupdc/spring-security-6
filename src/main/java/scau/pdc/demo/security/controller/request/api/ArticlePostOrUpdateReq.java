package scau.pdc.demo.security.controller.request.api;

import lombok.Data;

@Data
public class ArticlePostOrUpdateReq {

    private Long id;
    private String title;
    private String content;

}
