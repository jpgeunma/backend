package com.jpmarket.web;

import com.jpmarket.config.auth.LoginUser;
import com.jpmarket.config.auth.dto.SessionUser;
import com.jpmarket.service.FavoritesService;
import com.jpmarket.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class IndexController {

    private final PostsService postsService;
    private final FavoritesService favoritesService;
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findallDesc());
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/favorites")
    public String favorites(Model model, @LoginUser SessionUser user)
    {
        model.addAttribute("favorites",favoritesService.findAllByUserId(user.getId()));

        return "favorites";
    }

}
