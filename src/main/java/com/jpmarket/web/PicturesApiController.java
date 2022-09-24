package com.jpmarket.web;

import com.jpmarket.config.auth.dto.CustomUserDetails;
import com.jpmarket.domain.pictures.Pictures;
import com.jpmarket.service.PicturesService;
import com.jpmarket.service.PostsService;
import com.jpmarket.web.picturesDto.PicturesResponseDto;
import com.jpmarket.web.picturesDto.PicturesUploadRequestDto;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.jpmarket.config.response.BaseResponseStatus.INVALID_JWT;

@RequiredArgsConstructor
@RestController
public class PicturesApiController {

    private final PicturesService picturesService;
    private final PostsService postsService;

    @Value("${com.pictures.upload.path}")
    private String uploadPath;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @PostMapping(value = "/api/v1/pictures/upload")
    public ResponseEntity<List<PicturesResponseDto>> uploadPostsFile(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                @RequestPart(value = "file") MultipartFile uploadFile,
                                                                @RequestPart(value = "requestDto") PicturesUploadRequestDto requestDto){
        // check userId
        Long userId = customUserDetails.getId();
        Long postId = requestDto.getPostId();
        if(!Objects.equals(userId, postsService.findById(postId).getUserId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        List<PicturesResponseDto> responseDtos = new ArrayList<>();
        Long boardId = requestDto.getPostId();
        LocalDateTime createdDateTime = LocalDateTime.now();
        logger.info("boardId: ", requestDto.toEntity());
        // check type of file
        if(uploadFile.getContentType().startsWith("image") == false) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        String originalName = uploadFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        String folderPath = makeFolder(createdDateTime.toLocalDate());
        String saveName = uploadPath + File.separator + folderPath + File.separator + boardId + "_" + fileName;
        String thumbnailSaveName = "";
        Path savePath = Paths.get(saveName);

        try{
            uploadFile.transferTo(savePath);
            // 썸네일 생성 -> 썸네일 파일 이름은 s_로 시작
            thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + boardId + "_" + fileName;
            File thumbnailFile = new File(thumbnailSaveName);
            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);

            responseDtos.add(new PicturesResponseDto(boardId, fileName, folderPath));
        }catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        requestDto.setFolderPath(folderPath);
        requestDto.setUploadedDate(createdDateTime);
        requestDto.setOriginalFileName(fileName);
        requestDto.setSaveName(saveName);
        requestDto.setSaltedFileName(thumbnailSaveName);
        requestDto.setUploadedDate(createdDateTime);
        picturesService.upload(requestDto);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/pictures/test/{fileOriginName}")
    public ResponseEntity<Resource> getImageByName(@PathVariable("fileOriginName") String fileName) {
        Pictures pictures = picturesService.findByOriginalFileName(fileName);
        String storedFolderPath = pictures.getStoredFolderPath();
        logger.debug(fileName);
        logger.debug(storedFolderPath);
        logger.info("fileName: "+fileName);
        logger.info("saltedFolderPath: "+storedFolderPath);
        try{
            FileSystemResource resource = new FileSystemResource(storedFolderPath);
            if (!resource.exists()) {
                throw new FileNotFoundException();
            }
            HttpHeaders headers = new HttpHeaders();
            Path filePath = Paths.get(storedFolderPath);
            headers.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("fileName: "+fileName);
            System.out.println("saltedFolderPath: "+storedFolderPath);
            return null;
        }
    }

    @GetMapping(value = "/api/v1/pictures/{boardId}")
    public ResponseEntity<Resource> getImageByBoardId(@PathVariable Long boardId){
        List<Pictures> pictures = picturesService.findByBoardId(boardId);
        // TODO
        // originally designed to send multiple images
        String storedFolderPath = pictures.get(0).getStoredFolderPath();
        try{
            FileSystemResource resource = new FileSystemResource(storedFolderPath);
            if (!resource.exists()) {
                throw new FileNotFoundException();
            }
            HttpHeaders headers = new HttpHeaders();
            Path filePath = Paths.get(storedFolderPath);
            headers.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

        } catch (Exception e) {
            return null;
        }
    }

    private String makeFolder(LocalDate localDate) {
        String str = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

}
