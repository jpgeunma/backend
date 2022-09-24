package com.jpmarket.web.postsDto;
import com.jpmarket.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private Long userId;

    private Long location;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author,
                               Long userId, Long location) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.userId = userId;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .userId(userId)
                .build();
    }

}