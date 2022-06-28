package com.jpmarket.web.picturesDto;


import com.jpmarket.domain.pictures.Pictures;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class PicturesUploadRequestDto {

    private Long boardId;

    private String originalFileName;

    private String saltedFileName;

    private String saveName;
    private String folderPath;

    private LocalDateTime uploadedDate;

    @Builder
    public PicturesUploadRequestDto(Long boardId, String originalFileName) {
        this.boardId = boardId;
        this.originalFileName = originalFileName;
    }

    public Pictures toEntity() {
        return Pictures.builder()
                .boardId(boardId)
                .originalFileName(originalFileName)
                .saltedFileName(saltedFileName)
                .savedFileName(saveName)
                .storedFolderPath(folderPath)
                .build();

    }

}
