package com.jpmarket.web;
import com.jpmarket.config.auth.LoginUser;
import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.config.response.BaseResponse;
import com.jpmarket.config.response.BaseResponseStatus;
import com.jpmarket.service.PostsService;
import com.jpmarket.web.postsDto.PostsListResponseDto;
import com.jpmarket.web.postsDto.PostsResponseDto;
import com.jpmarket.web.postsDto.PostsSaveRequestDto;
import com.jpmarket.web.postsDto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts/save")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public BaseResponse<String> update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        try{
            Long userId = customUserDetails.getId();
            System.out.println("User who reqeust update: " + customUserDetails.getEmail());
            if(!Objects.equals(userId, requestDto.getUserId())){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }

            postsService.update(id, requestDto);
            String result = id + " has changed.";
            return new BaseResponse<>(result);
        }catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(BaseResponseStatus.FAIL);
        }

    }

    @DeleteMapping("/api/v1/posts/{id}")
    public BaseResponse<?> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {
        if(id == null)
            return new BaseResponse<>(BaseResponseStatus.EMPTY_IDX);
        if(id < 0)
            return new BaseResponse<>(BaseResponseStatus.INVALID_IDX);

        Long userId = customUserDetails.getId();
        if(!Objects.equals(userId, postsService.findById(id).getUserId())) {
            System.out.println("User " + userId + " is deleting post " + id);
            return new BaseResponse<>(BaseResponseStatus.USER_NOT_MATCH);
        }

        try {
            postsService.delete(id);
            return new BaseResponse<>("delete success");
        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(BaseResponseStatus.FAIL);
        }

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
