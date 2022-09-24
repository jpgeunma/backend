package com.jpmarket.web;
import com.jpmarket.config.auth.LoginUser;
import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.service.PostsService;
import com.jpmarket.web.postsDto.PostsListResponseDto;
import com.jpmarket.web.postsDto.PostsResponseDto;
import com.jpmarket.web.postsDto.PostsSaveRequestDto;
import com.jpmarket.web.postsDto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts/save")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto, @LoginUser CustomUserDetails customUserDetails) {
        System.out.println("User who reqeust update: " + customUserDetails.);
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
    @GetMapping("/api/v1/posts/hot")
    public ResponseEntity<List<PostsListResponseDto>> findTenPostsByView () {
        return new ResponseEntity<>(postsService.findTenPostsByViewed(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
    }

}
