package com.jpmarket.web.commentDto;

import com.jpmarket.domain.comment.Comments;
import lombok.Getter;

@Getter
public class CommentSaveRequestDto {

    private Long postId;

    private String writer;

    private String content;

    public CommentSaveRequestDto(Comments entity) {
        this.postId = entity.getPostId();
        this.writer = entity.getWriter();
        this.content = entity.getContent();
    }

    public Comments toEntity() {
        return Comments.builder()
                .postId(postId)
                .writer(writer)
                .content(content)
                .build();
    }
}
