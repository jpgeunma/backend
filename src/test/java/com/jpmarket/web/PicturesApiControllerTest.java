package com.jpmarket.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmarket.domain.pictures.Pictures;
import com.jpmarket.domain.pictures.PicturesRepository;
import com.jpmarket.service.PicturesService;
import com.jpmarket.web.picturesDto.PicturesUploadRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PicturesApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PicturesRepository picturesRepository;

    @MockBean
    private PicturesService picturesService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        picturesRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Pictures_uploaded() throws Exception {

        // given
        Long boardId = 1L;

        String fileName = "testImage1";

        String folderPath = "testImages";

        String url = "http://localhost:" + port + "/api/v1/pictures/upload";

        // given image
        File file = new File("C:\\Users\\Kangjeuk\\IdeaProjects\\jp-\\backend\\src\\main\\resources\\testImages\\testImage1.png");
        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte [] pictureByte = outputStream.toByteArray();

        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.png", MediaType.MULTIPART_FORM_DATA_VALUE, pictureByte);

        PicturesUploadRequestDto requestDto = PicturesUploadRequestDto.builder()
                .boardId(boardId)
                .fileName(fileName)
                .folderPath(folderPath)
                .build();

        mvc.perform(post(url)
                        .param("requestDto", new ObjectMapper().writeValueAsString(requestDto))
                        .param("uploadFile", multipartFile.toString()))
                .andExpect(status().isOk());

        List<Pictures> all = picturesRepository.findAll();
        assertThat(all.get(0).getBoardId()).isEqualTo(boardId);
        assertThat(all.get(0).getOriginalFileName()).isEqualTo(fileName);

    }
}
