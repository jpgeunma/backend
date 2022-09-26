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
    private String email;
    private Long price;
    private Long location;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author,
                               String email, Long price, Long location) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.price = price;
        this.email = email;
        this.location = location;
    }

    public Posts toEntity(Long userId) {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .price(price)
                .location(location)
                .userId(userId)
                .build();
    }

}