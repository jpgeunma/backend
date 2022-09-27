package com.jpmarket.web.picturesDto;

import com.jpmarket.domain.BaseTimeEntity;
import com.jpmarket.domain.pictures.Pictures;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class PicturesResponseDto {

    private Long boardId;

    private Long idx;
    private String fileName;

    private String storedFolderPath;

    private String saltedFileName;


    public String getImageURL() {
        try{
            return URLEncoder.encode(storedFolderPath + "/" + boardId + fileName, "UTF-8");

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public PicturesResponseDto(Pictures pictures)
    {
        this.idx = pictures.getIdx();
        this.boardId = pictures.getBoardId();
        this.fileName = pictures.getOriginalFileName();
        this.storedFolderPath = pictures.getStoredFolderPath();
        this.saltedFileName = pictures.getSaltedFileName();
    }
    public Pictures toEntity() {
        return Pictures.builder()
                .boardId(boardId)
                .idx(idx)
                .originalFileName(fileName)
                .storedFolderPath(storedFolderPath)
                .saltedFileName(saltedFileName)
                .build();
    }

}
