package com.jpmarket.web;

import com.jpmarket.config.auth.dto.JwtToken;
import com.jpmarket.config.jwt.JwtProperties;
import com.jpmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/auth/token")
    public ResponseEntity getLogin(@RequestParam("code") String code) {

        String jwtToken = userService.getToken(code);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        return ResponseEntity.ok().headers(headers).body("success");
    }
}
