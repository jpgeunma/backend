package com.jpmarket.service;

import com.jpmarket.domain.pictures.PicturesRepository;
import com.jpmarket.web.picturesDto.PicturesUploadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PicturesService {

    private final PicturesRepository picturesRepository;

    @Transactional
    public Long upload(PicturesUploadRequestDto requestDto) {
        return picturesRepository.save(requestDto.toEntity()).getId();
    }
}
