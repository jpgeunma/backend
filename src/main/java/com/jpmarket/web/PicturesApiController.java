package com.jpmarket.web;

import com.jpmarket.service.PicturesService;
import com.jpmarket.web.picturesDto.PicturesResponseDto;
import com.jpmarket.web.picturesDto.PicturesUploadRequestDto;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PicturesApiController {

    private final PicturesService picturesService;

    @Value("${com.pictures.upload.path}")
    private String uploadPath;

    @PostMapping(value = "/api/v1/pictures/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<PicturesResponseDto>> uploadFile(@RequestParam("file") MultipartFile uploadFile,
                                                                PicturesUploadRequestDto requestDto){

        List<PicturesResponseDto> responseDtos = new ArrayList<>();
        Long boardId = requestDto.getBoardId();
        LocalDate createdDate = requestDto.toEntity().getCreatedDate().toLocalDate();

        // check type of file
        if(uploadFile.getContentType().startsWith("image") == false) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        String originalName = uploadFile.getOriginalFilename();

        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

        String folderPath = makeFolder(createdDate);

        String saveName = uploadPath + File.separator + folderPath + File.separator + boardId + "_" + fileName;

        Path savePath = Paths.get(saveName);

        try{
            uploadFile.transferTo(savePath);

            // 썸네일 생성 -> 썸네일 파일 이름은 s_로 시작
            String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + boardId + "_" + fileName;

            File thumbnailFile = new File(thumbnailSaveName);

            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);

            responseDtos.add(new PicturesResponseDto(boardId, fileName, folderPath));
        }catch (IOException e) {
            e.printStackTrace();
        }

        picturesService.upload(requestDto);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    private String makeFolder(LocalDate localDate) {
        String str = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

}
