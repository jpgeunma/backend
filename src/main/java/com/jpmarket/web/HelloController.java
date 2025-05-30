package com.jpmarket.web;
import com.jpmarket.web.postsDto.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public ResponseDto helloDto(@RequestParam("name") String name,
                                @RequestParam("amount") int amount) {
        return new ResponseDto(name, amount);
    }

}
