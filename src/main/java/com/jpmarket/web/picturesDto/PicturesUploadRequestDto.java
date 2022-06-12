package com.jpmarket.web.picturesDto;


import com.jpmarket.domain.pictures.Pictures;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class PicturesUploadRequestDto {

    private Long boardId;

    private String fileName;

    private String folderPath;

    private LocalDateTime uploadedDate;

    @Builder
    public PicturesUploadRequestDto(Long boardId, String fileName) {
        this.boardId = boardId;
        this.fileName = fileName;
    }

    public Pictures toEntity() {
        return Pictures.builder()
                .boardId(boardId)
                .originalFileName(fileName)
                .storedFolderPath(folderPath)
                .build();

    }

}
