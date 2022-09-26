package com.jpmarket.web.postsDto;

import com.jpmarket.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;

    private String content; // TODO 테스트 용 코드 나중에 미리보기용으로 만든 Dto임으로 삭제
                            // 그리고 view횟수 추가
    private Long price;
    private LocalDateTime modifiedDate;

    private Long commentsNum;

    private Long favoritesNum;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.price = entity.getPrice();
        this.modifiedDate = entity.getModifiedDate();
        this.commentsNum = entity.getCommentsNum();
        this.favoritesNum = entity.getFavoritesNum();
    }
}
