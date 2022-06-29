package com.jpmarket.domain.comment;

import com.jpmarket.domain.BaseTimeEntity;
import com.jpmarket.domain.posts.Posts;
import com.jpmarket.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long postId;

    @Column
    private Long userId;

    @Column
    private String writer;

    @Column
    private String content;


    @Builder
    public Comments(Long postId, Long userId, String writer, String content) {
        this.postId = postId;
        this.userId = userId;
        this.writer = writer;
        this.content = content;
    }

}
