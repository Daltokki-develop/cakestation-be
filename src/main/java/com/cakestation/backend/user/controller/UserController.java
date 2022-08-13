package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UserService;
import com.cakestation.backend.user.service.UtilService;
import com.cakestation.backend.user.dto.response.KakaoUserDto;
import com.cakestation.backend.user.dto.response.CheckDto;
import com.cakestation.backend.user.dto.response.TokenDto;
import static com.cakestation.backend.config.KakaoConfig.REDIRECT_LOGINPAGE;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;



@RestController
@RequiredArgsConstructor
public class UserController {
    private final KakaoService kakaoService;
    private final UserService userService;
    private final UtilService utilService;

    //인가 코드 반환
    @GetMapping("/login")
    public RedirectView redirectLogin(HttpServletRequest request) {

        TokenDto tokenDto;
        //추후 refresh토큰 확인 후 바로 accessToken발급 상태로 전환
        // Cookie[] cookies = request.getCookies();
        // !!! response Data에 accessToken만 존재한다
        // System.out.println("Cookies::::" + cookies);
        
        // if(cookies != null){
        //     for(Cookie ele: cookies){
        //         if("refresh".equals(ele.getName())){
        //             System.out.println("Refresh Token사용");
        //             String refreshValue = ele.getValue();
        //             tokenDto = kakaoService.refreshAccessToken(refreshValue);
        //         }
        //     }
        // }
        //없을 경우 아래 redirect url을 통해 새로운 토큰 발급
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(REDIRECT_LOGINPAGE);
    
        return redirectView;
    }

    
    //"login" API에서 redirect된 URL에서 Code parameter를 추출하여 아래 API문을 실행하여 유저정보를 저장하고 AccessToken을 획득하여 쿠키로 보내준다.
    @GetMapping("/oauth/kakao")
    public ResponseEntity KakaoCallback(@RequestParam String code,  HttpServletResponse response) {
        
        KakaoUserDto kakaoUserDto = null;

        //Token획득 메드 호출
        TokenDto tokenDto = kakaoService.getKaKaoAccessToken(code);
        //얻은 토큰을 통한 유저정보 조회 및 저장
        kakaoUserDto =  kakaoService.getUserInfo(tokenDto.getAccessToken());
   
        HashMap cookies = utilService.makeCookie(tokenDto); 
        // accessCookie =
        // refreshCookie = 
        response.addCookie((Cookie) cookies.get("access"));
        response.addCookie((Cookie) cookies.get("refresh"));
   
        Long userId = userService.join(kakaoUserDto);

        return new ResponseEntity<>(new ApiResponse(200,"로그인 성공",kakaoUserDto),HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout/kakao")
    public ResponseEntity KakaoLogout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        CheckDto result = null;
        Cookie accesscookie = null;
        Cookie refreshcookie = null;

        try{
            //토큰을 찾는 메서드 
            String findCookie = utilService.CookieAccessToken(cookies, "Authorization");
            
            //찾은 토큰을 통한 로그아웃 메서드
            result = kakaoService.LogoutToken(findCookie);
            
            
        }catch(NullPointerException e){
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
        return new ResponseEntity<>(new ApiResponse(200,"로그아웃 성공",result),HttpStatus.OK);
    }

}
