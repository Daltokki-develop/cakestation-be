package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.config.KakaoConfig;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UserService;
import com.cakestation.backend.user.service.UtilService;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import com.cakestation.backend.user.service.dto.response.CheckDto;
import com.cakestation.backend.user.service.dto.response.TokenDto;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final KakaoService kakaoService;
    private final UserService userService;
    private final UtilService utilService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final KakaoConfig kakaoConfig;

    @GetMapping("/stores/test")
    public ResponseEntity getEmail(HttpServletRequest request) throws Exception {
        String email = utilService.getCurrentUserEmail(request);

        int test = utilService.makeNickName(request);

        return new ResponseEntity<>(new ApiResponse(200, "이메일 획득", email), HttpStatus.OK);
    }

    //인가 코드 반환
    @GetMapping("/api/login")
    public RedirectView redirectLogin(HttpServletRequest request) {
        System.out.println("::::::::::::: /login 컨트롤러 동작 확인");
        TokenDto tokenDto;
        //추후 refresh토큰 확인 후 바로 accessToken발급 상태로 전환
        //없을 경우 아래 redirect url을 통해 새로운 토큰 발급
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(kakaoConfig.REDIRECT_LOGINPAGE);

        return redirectView;
    }


    //"login" API에서 redirect된 URL에서 Code parameter를 추출하여 아래 메서드를 실행하여 유저정보를 저장및 토큰을 쿠키로 보내준다.
    @GetMapping("/api/oauth")
    public ResponseEntity KakaoCallback(@RequestParam String code, HttpServletResponse response) {
        System.out.println("::::::::::::: /oauth/kakao 컨트롤러 동작 확인");
        KakaoUserDto kakaoUserDto = null;

        //Token획득 메드 호출
        TokenDto tokenDto = kakaoService.getKaKaoAccessToken(code);
        //얻은 토큰을 통한 유저정보 조회 및 저장
        kakaoUserDto = kakaoService.getUserInfo(tokenDto.getAccessToken());

        HashMap cookies = utilService.makeCookie(tokenDto);
        response.addCookie((Cookie) cookies.get("access"));
        response.addCookie((Cookie) cookies.get("refresh"));


//        response.setHeader("Set-Cookie", cookie.toString());
        Long userId = userService.join(kakaoUserDto);

        return new ResponseEntity<>(new ApiResponse(200, "로그인 성공", userId), HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout/kakao")
    public ResponseEntity KakaoLogout(HttpServletRequest request, HttpServletResponse response) {
        CheckDto result = null;
        Cookie accesscookie = null;
        Cookie refreshcookie = null;


        try {
            //토큰을 찾는 메서드 
            String findCookie = utilService.cookieAccessToken(request, "Authorization");

            //찾은 토큰을 통한 로그아웃 메서드
            result = kakaoService.LogoutToken(findCookie);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //로그아웃 후 웹 쿠키에 남아있는 Token을 삭제
        accesscookie = new Cookie("Authorization", null); // 삭제할 쿠키에 대한 값을 null로 지정
        accesscookie.setPath("/"); // 전체 주소에 적용된 쿠키를 삭제
        accesscookie.setMaxAge(0); // 유효시간을 0으로 설정해서 바로 만료시킨다.

        refreshcookie = new Cookie("refresh", null);
        refreshcookie.setPath("/");
        refreshcookie.setMaxAge(0);


        response.addCookie(accesscookie); // 응답에 추가해서 없어지도록 함
        response.addCookie(refreshcookie);
        return new ResponseEntity<>(new ApiResponse(200, "로그아웃 성공", result), HttpStatus.OK);
    }

}
