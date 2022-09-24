package com.jpmarket.web.favoritesDto;

import com.jpmarket.domain.favorites.Favorites;
import com.jpmarket.domain.posts.PostsRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoritesSaveRequestDto {

    private Long userId;

    private Long postId;

    @Builder
    public FavoritesSaveRequestDto(Long userId, Long postId)
    {
        this.userId = userId;
        this.postId = postId;
    }

//    public Favorites toEntity() {
//        return Favorites.builder()
//                .userId(userId)
//                .postId(postId)
//                .build();
//    }

}
