package com.jpmarket.service;

import com.jpmarket.domain.favorites.Favorites;
import com.jpmarket.domain.favorites.FavoritesRepository;
import com.jpmarket.web.favoritesDto.FavoritesListResponseDto;
import com.jpmarket.web.favoritesDto.FavoritesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;

    @Transactional
    public Long save(FavoritesSaveRequestDto requestDto)
    {
        return favoritesRepository.save(requestDto.toEntity()).getId();
    }


    @Transactional(readOnly = true)
    public List<FavoritesListResponseDto> findAllByUserId(Long userId) {
        favoritesRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 유저는 없습니다"));

        return favoritesRepository.findAllByUserId(userId).stream()
                .map(FavoritesListResponseDto::new)
                .collect(Collectors.toList());
    }

}
