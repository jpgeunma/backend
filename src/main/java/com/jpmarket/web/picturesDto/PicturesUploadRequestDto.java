package com.jpmarket.web.picturesDto;


import com.jpmarket.domain.pictures.Pictures;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PicturesUploadRequestDto {

    private Long id;
    private Long postId;

    private String originalFileName;

    private String saltedFileName;

    private String saveName;
    private String folderPath;

    private LocalDateTime uploadedDate;

    @Builder
    public PicturesUploadRequestDto(Long boardId, String originalFileName) {
        this.postId = boardId;
        this.originalFileName = originalFileName;
    }

    public Pictures toEntity() {
        return Pictures.builder()
                .boardId(postId)
                .originalFileName(originalFileName)
                .saltedFileName(saltedFileName)
                .savedFileName(saveName)
                .storedFolderPath(folderPath)
                .build();

    }

}
