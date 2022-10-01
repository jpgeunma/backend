package com.jpmarket.service;

import com.jpmarket.domain.favorites.Favorites;
import com.jpmarket.domain.favorites.FavoritesRepository;
import com.jpmarket.domain.posts.Posts;
import com.jpmarket.domain.user.User;
import com.jpmarket.web.favoritesDto.FavoritesListResponseDto;
import com.jpmarket.web.favoritesDto.FavoritesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;

    @Transactional
    public Long save(User user, Posts posts)
    {
        Favorites favorites = Favorites.builder()
                .user(user)
                .posts(posts)
                .build();
        if(favoritesRepository.findByUserAndPosts(user, posts).isPresent())
            return favoritesRepository.deleteFavoritesByUserAndPosts(user, posts);
        return favoritesRepository.save(favorites).getId();
    }


    @Transactional(readOnly = true)
    public List<FavoritesListResponseDto> findAllByUserId(Long userId) {
        favoritesRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 유저는 없습니다"));

        return favoritesRepository.findAllByUserId(userId).stream()
                .map(FavoritesListResponseDto::new)
                .collect(Collectors.toList());
    }

    //FIXME not added to Controller Yet
    @Transactional(readOnly = true)
    public Long findAllLengthByUserId(Long userId) {
        favoritesRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 유저는 없습니다"));

        return favoritesRepository.findAllLengthByUserId(userId);
    }

    @Transactional
    public Optional<Favorites> findByUserIdAndPostId(User user, Posts posts)
    {
        return favoritesRepository.findByUserAndPosts(user, posts);
    }
    @Transactional
    public void delete(User user, Posts posts)
    {
        favoritesRepository.deleteFavoritesByUserAndPosts(user, posts);
    }
}
