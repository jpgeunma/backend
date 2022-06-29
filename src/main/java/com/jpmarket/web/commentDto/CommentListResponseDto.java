package com.jpmarket.web.commentDto;

import com.jpmarket.domain.comment.Comments;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class CommentListResponseDto {

    private Long postId;

    private String writer;

    private String content;

    public CommentListResponseDto(Comments entity) {
        this.postId = entity.getPostId();
        this.writer = entity.getWriter();
        this.content = entity.getContent();
    }
}
