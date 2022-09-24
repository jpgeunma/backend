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

    private Long userId;

    private Long status;

    private Long location;

    private Long category;

    private Long buyerId;

    private Long viewed;

    private Long commentsNum;

    private Long favoritesNum;
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.cost = entity.getCost();
        this.author = entity.getAuthor();
        this.userId = entity.getUserId();
        this.status = entity.getStatus();
        this.location = entity.getLocation();
        this.category = entity.getCategory();
        this.buyerId = entity.getBuyerId();
        this.viewed = entity.getViewed();
        this.commentsNum = entity.getCommentsNum();
        this.favoritesNum = entity.getFavoritesNum();
    }

    public Posts toEntity() {
        return Posts.builder()
                .id(this.id)
                .userId(this.userId)
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .buyerId(this.buyerId)
                .category(this.category)
                .location(this.location)
                .build();
    }
}
