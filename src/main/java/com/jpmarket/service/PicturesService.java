package com.jpmarket.service;

import com.jpmarket.domain.pictures.Pictures;
import com.jpmarket.domain.pictures.PicturesRepository;
import com.jpmarket.web.picturesDto.PicturesResponseDto;
import com.jpmarket.web.picturesDto.PicturesUploadRequestDto;
import com.jpmarket.web.postsDto.PostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PicturesService {

    private final PicturesRepository picturesRepository;

    @Transactional
    public Long upload(PicturesUploadRequestDto requestDto) {
        return picturesRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Pictures findByOriginalFileName(String fileName) {
        return picturesRepository.findByOriginalFileName(fileName);
    }

    @Transactional
    public List<PicturesResponseDto> findByBoardId(Long boardId) {
        return picturesRepository.findallByBoardId(boardId).
                stream().map(PicturesResponseDto::new)
                .collect(Collectors.toList());
    }
}
