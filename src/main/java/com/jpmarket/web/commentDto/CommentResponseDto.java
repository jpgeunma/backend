package com.jpmarket.web.commentDto;

import com.jpmarket.domain.comment.Comments;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long postId;

    private String writer;

    private String content;

    public CommentResponseDto(Comments entity) {
        this.postId = entity.getPostId();
        this.writer = entity.getWriter();
        this.content = entity.getContent();
    }
}
