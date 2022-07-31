package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UserService;
import com.cakestation.backend.user.dto.response.KakaoUserDto;
import com.cakestation.backend.user.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<>(new ApiResponse(200,"로그인 성공",kakaoUserDto), httpHeaders, HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout/kakao")
    public ResponseEntity logout(){
        return ResponseEntity.ok().build();
    }
}
