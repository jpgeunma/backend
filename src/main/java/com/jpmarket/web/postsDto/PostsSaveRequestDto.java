package com.jpmarket.web.postsDto;
import com.jpmarket.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    private Long location;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author,
                               Long location) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.location = location;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .location(location)
                .build();
    }

}