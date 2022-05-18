package com.jpmarket.web.dto;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sun.jvm.hotspot.HelloWorld;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ResponseDtoTest {

    @Test
    public void lombokTest(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        ResponseDto dto = new ResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
