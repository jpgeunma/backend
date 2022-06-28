package com.jpmarket.web.postsDto;

import com.jpmarket.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;

    private Long cost;
    private String author;

    private Long status;

    private Long location;

    private Long category;

    private Long buyerId;
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.cost = entity.getCost();
        this.author = entity.getAuthor();
        this.status = entity.getStatus();
        this.location = entity.getLocation();
        this.category = entity.getCategory();
        this.buyerId = entity.getBuyerId();
    }
}
