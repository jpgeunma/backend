package com.jpmarket.service;

import com.jpmarket.domain.posts.Posts;
import com.jpmarket.domain.posts.PostsRepository;
import com.jpmarket.web.postsDto.PostsListResponseDto;
import com.jpmarket.web.postsDto.PostsResponseDto;
import com.jpmarket.web.postsDto.PostsSaveRequestDto;

import com.jpmarket.web.postsDto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto, Long userId) {
        return postsRepository.save(requestDto.toEntity(userId)).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 업습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findTenPostsByViewed() {
        return postsRepository.findPostsByViewed().subList(0, 9).stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findallDesc() {
        return postsRepository.findallDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
