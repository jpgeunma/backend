package com.jpmarket.web.picturesDto;


import com.jpmarket.domain.pictures.Pictures;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PicturesUploadRequestDto {

    private Long boardId;

    private String fileName;

    private String folderPath;


    @Builder
    public PicturesUploadRequestDto(Long boardId, String fileName, String folderPath) {
        this.boardId = boardId;
        this.fileName = fileName;
        this.folderPath = folderPath;
    }

    public Pictures toEntity() {
        return Pictures.builder().
                boardId(boardId)
                .originalFileName(fileName)
                .storedFolderPath(folderPath)
                .build();
    }

}
