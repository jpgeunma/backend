package com.jpmarket.web;

import com.jpmarket.service.FavoritesService;
import com.jpmarket.service.PostsService;
import com.jpmarket.web.favoritesDto.FavoritesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FavoritesApiController {

    private final PostsService postsService;
    private final FavoritesService favoritesService;

    @PostMapping("/api/v1/favorites")
    public Long save(@RequestBody FavoritesSaveRequestDto requestDto)
    {
        return favoritesService.save(requestDto);
    }

    @DeleteMapping("/api/v1/favorites/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }



}
