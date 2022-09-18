package com.jpmarket.service;

import com.jpmarket.domain.pictures.Pictures;
import com.jpmarket.domain.pictures.PicturesRepository;
import com.jpmarket.web.picturesDto.PicturesUploadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<Pictures> findByBoardId(Long boardId) {
        return picturesRepository.findallByBoardId(boardId);
    }
}
