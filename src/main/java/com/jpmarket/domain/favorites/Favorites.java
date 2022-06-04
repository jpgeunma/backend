package com.jpmarket.domain.favorites;

import com.jpmarket.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Favorites extends BaseTimeEntity {

    @Id //TODO not sure?
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long postId;

    @Builder
    public Favorites(Long userId, Long postId)
    {
        this.userId = userId;
        this.postId = postId;
    }
}
