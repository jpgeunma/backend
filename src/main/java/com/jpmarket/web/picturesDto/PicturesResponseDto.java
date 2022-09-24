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
public class PicturesResponseDto extends BaseTimeEntity {

    private Long boardId;

    private String fileName;

    private String folderPath;

    public String getImageURL() {
        try{
            return URLEncoder.encode(folderPath + "/" + boardId + fileName, "UTF-8");

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }



}
