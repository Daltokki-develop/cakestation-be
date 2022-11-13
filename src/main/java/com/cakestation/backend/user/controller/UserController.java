package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.config.JwtProperties;
import com.cakestation.backend.config.KakaoConfig;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UserService;
import com.cakestation.backend.user.service.UtilService;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import com.cakestation.backend.user.service.dto.response.CheckDto;
import com.cakestation.backend.user.service.dto.response.TokenDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final KakaoService kakaoService;
    private final UserService userService;
    private final UtilService utilService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final KakaoConfig kakaoConfig;

    @GetMapping("/email")
    public ResponseEntity getEmail(@RequestHeader("Authorization") String token) throws Exception {

        String email = utilService.getCurrentUserEmail(token);

//        int test = utilService.makeNickName(request);
        System.out.println(email);
        return new ResponseEntity<>(new ApiResponse<>(200, "이메일 획득", email), HttpStatus.OK);
    }


    //"login" API에서 redirect된 URL에서 Code parameter를 추출하여 아래 메서드를 실행하여 유저정보를 저장및 토큰을 쿠키로 보내준다.
    @GetMapping("/oauth")
    public ResponseEntity KakaoCallback(@RequestParam String code) {

        //Token 획득
        TokenDto tokenDto = kakaoService.getKaKaoAccessToken(code);
        KakaoUserDto userInfo = kakaoService.getUserInfo(tokenDto.getAccessToken());
        Long userId = userService.join(userInfo);

        //얻은 토큰을 통한 유저정보 조회 및 저장
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + tokenDto.getAccessToken());

        //닉네임 설정
        utilService.makeNickname(userInfo.getEmail(), false);

       return new ResponseEntity<>(
                new ApiResponse<>(200, "로그인 성공", tokenDto.getAccessToken()), httpHeaders, HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout/kakao")
    public ResponseEntity KakaoLogout(HttpServletRequest request, HttpServletResponse response) {
        CheckDto result = null;
        Cookie accesscookie = null;
        Cookie refreshcookie = null;


        try {
            //토큰을 찾는 메서드 
            String findCookie = String.valueOf(utilService.headerAccessToken(request, "Authorization"));

            //찾은 토큰을 통한 로그아웃 메서드
            result = kakaoService.LogoutToken(findCookie);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ApiResponse(200, "로그아웃 성공", result), HttpStatus.OK);
    }

    @GetMapping("/nickname")
    public void makenickname(@RequestHeader("Authorization") String token){
        String email = utilService.getCurrentUserEmail(token);

        utilService.makeNickname(email , true);

    }

}
