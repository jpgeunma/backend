package com.jpmarket.domain.favorites;

import com.jpmarket.domain.BaseTimeEntity;
import com.jpmarket.domain.posts.Posts;
import com.jpmarket.domain.user.User;
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

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @OneToOne(targetEntity = Posts.class, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(nullable = false, name = "posts_id")
    private Posts posts;

    @Builder
    public Favorites(User user, Posts posts)
    {
        this.user = user;
        this.posts = posts;
    }
}
