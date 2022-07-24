package com.cakestation.backend.controller;

import com.cakestation.backend.controller.dto.ResponseDto;
import com.cakestation.backend.service.KakaoService;
import com.cakestation.backend.service.UserService;
import com.cakestation.backend.service.dto.response.KakaoUserDto;
import com.cakestation.backend.service.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final KakaoService kakaoService;
    private final UserService userService;

    //인가 코드 반환
    @GetMapping("/oauth/kakao")
    public ResponseEntity KakaoCallback(@RequestParam String code) {
        TokenDto tokenDto = kakaoService.getKaKaoAccessToken(code);
        KakaoUserDto kakaoUserDto =  kakaoService.getUserInfo(tokenDto.getAccessToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + tokenDto.getAccessToken());

        Long userId = userService.join(kakaoUserDto);
        return new ResponseEntity<>(new ResponseDto(200,true,"로그인 성공",kakaoUserDto), httpHeaders, HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout/kakao")
    public ResponseEntity logout(){
        return ResponseEntity.ok().build();
    }
}
