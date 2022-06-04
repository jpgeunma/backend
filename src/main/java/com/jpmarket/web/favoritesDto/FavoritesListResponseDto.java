package com.jpmarket.web.favoritesDto;

import com.jpmarket.domain.favorites.Favorites;
import lombok.Getter;

@Getter
public class FavoritesListResponseDto {

    private Long userId;

    private Long postId;

    public FavoritesListResponseDto(Favorites entity) {
        this.userId = entity.getUserId();
        this.postId = entity.getPostId();
    }
}
