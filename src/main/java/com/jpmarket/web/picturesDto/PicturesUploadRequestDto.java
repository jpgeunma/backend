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

    private Long idx;
    private Long postId;

    private String originalFileName;

    private String saltedFileName;

    private String saveName;
    private String folderPath;

    private LocalDateTime uploadedDate;

    @Builder
    public PicturesUploadRequestDto(Long boardId, Long idx, String originalFileName, String saltedFileName
                                    , String saveName, String folderPath) {
        this.postId = boardId;
        this.idx = idx;
        this.originalFileName = originalFileName;
        this.saltedFileName = saltedFileName;
        this.saveName = saveName;
        this.folderPath = folderPath;
    }

    public Pictures toEntity() {
        return Pictures.builder()
                .boardId(postId)
                .idx(idx)
                .originalFileName(originalFileName)
                .saltedFileName(saltedFileName)
                .savedFileName(saveName)
                .storedFolderPath(folderPath)
                .build();

    }

}
