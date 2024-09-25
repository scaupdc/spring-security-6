package scau.pdc.demo.security.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    private Long id;
    private String title;
    private String content;
    private Date postDate;
    private Long userId;

}
