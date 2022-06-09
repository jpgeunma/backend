package com.jpmarket.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmarket.domain.favorites.Favorites;
import com.jpmarket.domain.favorites.FavoritesRepository;
import com.jpmarket.domain.posts.PostsRepository;
import com.jpmarket.domain.user.UserRepository;
import com.jpmarket.web.favoritesDto.FavoritesSaveRequestDto;
import com.jpmarket.web.postsDto.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FavoritesApiControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private FavoritesRepository favoritesRepository;

    @Autowired
    private UserRepository userRepository;
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
        favoritesRepository.deleteAll();
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Favorites_added() throws Exception {
        // given



        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .title("title")
                .content("content")
                .author("author")
                .location(1L)
                .build();

        String url = "http://localhost:" + port + "api/v1/posts";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(postsSaveRequestDto)))
            .andExpect(status().isOk());

        FavoritesSaveRequestDto favoritesSaveRequestDto = FavoritesSaveRequestDto.builder()
                .postId(1L) // first posted
                .userId(1L)
                .build();

        List<Favorites> all = favoritesRepository.findAllByUserId(1L);
        assertThat(all.get(0).getPostId()).isEqualTo(1);
        assertThat(all.get(0).getUserId()).isEqualTo(1);
    }
}
