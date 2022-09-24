package com.jpmarket.web;

import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.domain.posts.Posts;
import com.jpmarket.domain.user.User;
import com.jpmarket.service.FavoritesService;
import com.jpmarket.service.PostsService;
import com.jpmarket.service.UserService;
import com.jpmarket.web.favoritesDto.FavoritesListResponseDto;
import com.jpmarket.web.favoritesDto.FavoritesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FavoritesApiController {

    private final UserService userService;

    private final PostsService postsService;
    private final FavoritesService favoritesService;

    @PostMapping("/api/v1/favorites")
    public Long save(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody FavoritesSaveRequestDto requestDto)
    {
        System.out.println("userDetails Like: " + userDetails);
        User user = userService.findById(requestDto.getUserId()).toEntity();
        Posts posts = postsService.findById(requestDto.getPostId()).toEntity();
        if (user == null || posts == null) {
            System.out.println("is NULL user: " + user + " posts: " + posts);
            return 0L;
        }
        if (!user.getId().equals(userDetails.getId()))
        {
            System.out.println("user id is not same");
            return 0L;
        }
        return favoritesService.save(user, posts);
    }

    @DeleteMapping("/api/v1/favorites/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/favorites/list")
    public List<FavoritesListResponseDto> favoritesList(@AuthenticationPrincipal CustomUserDetails userDetail){
        return favoritesService.findAllByUserId(userDetail.getId());
    }


}
