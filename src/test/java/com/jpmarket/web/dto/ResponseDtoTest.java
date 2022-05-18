package com.jpmarket.web.dto;
import org.junit.Test;
import sun.jvm.hotspot.HelloWorld;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseDtoTest {

    @Test
    public void lombokTest(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        ResponseDto dto = new ResponseDto(name, amount);
    }
}
