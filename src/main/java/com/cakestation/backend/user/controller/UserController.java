package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.cakestation.backend.user.dto.response.KakaoUserDto;
import com.cakestation.backend.user.dto.response.TokenDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.*;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

import lombok.RequiredArgsConstructor;

import static com.cakestation.backend.config.KakaoConfig.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    
    private final KakaoService kakaoService;
    private final UserService userService;

    @RequestMapping("/login")
    public RedirectView exRedirect4() {
        RedirectView redirectView = new RedirectView();
        
        //추후 refresh토큰 확인 후 바로 accessToken발급 상태로 전환
        //없을 경우 아래 redirect url을 통해 새로운 토큰 발급
        redirectView.setUrl(REDIRECT_LOGINPAGE);

        return redirectView;
    }
    //인가 코드 반환
    @GetMapping("/oauth/kakao")
    public ResponseEntity KakaoCallback(@RequestParam(value = "code") String code, HttpServletResponse response) {
        
        JsonElement tokenResponse;
        JsonElement userResponse;
        TokenDto tokenDto;
        String accessCookie;
        //!!! 추후 refresh처리방안 논의할 예정 
        String refreshCookie;
        KakaoUserDto kakaoUserDto = null;
        
        try {
            
            // --- 토큰을 얻은뒤 setCookies에 Authorization의 Value 값으로 넣어주기 
            tokenResponse = HttpUtil.getTokenurl(GET_TOKEN_URL+"&code="+code);
            tokenDto = TokenDto.builder()
            .accessToken(tokenResponse.getAsJsonObject().get("access_token").getAsString())
            .refreshToken(tokenResponse.getAsJsonObject().get("refresh_token").getAsString())
            .build();
            
            //!!! 톰캣의 쿠키규칙으로 공백을 포함할수 없어서 utf-8로 인코딩을 해야함
            //!!! Bearer 정책을 활용하여 중간에 띄어쓰기를 위해 인코딩을하니 공백이 +로 변경되는 문제 발생
            accessCookie = URLEncoder.encode("Bearer " + tokenDto.getAccessToken(), "utf-8");
            refreshCookie = URLEncoder.encode("Bearer " + tokenDto.getRefreshToken(), "utf-8");
            Cookie setCookie = new Cookie("Authorization", accessCookie);
            
            // ->쿠키 시간을 하루로 지정(60초*60분*24시간) 임의 지정
            setCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(setCookie);
            
            // --- 토큰을 통한 유저정보 조회 및 저장
            userResponse = HttpUtil.sendKakao(GET_USER_URL,tokenDto.getAccessToken());
            JsonObject account = userResponse.getAsJsonObject().get("kakao_account").getAsJsonObject();
    
                String username = account.getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();
                String email = account.getAsJsonObject().get("email").getAsString();
                
            kakaoUserDto = KakaoUserDto.builder()
                        .username(username)
                        .email(email)
                        .build();
                        
            userService.join(kakaoUserDto);
 
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ApiResponse(200, "로그인 성공", kakaoUserDto), HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.ok().build();
    }

    private final UserRepository userRepository;
    @GetMapping("/userinfo")
    @ResponseBody
    //Entity 를 DTO로 만들어서 반환할 수 있도록 할것
    public List<User> userInfo(){
        //Service에 Userinfo를 호출해서 값을 만들수 있도록 할것
        List<User> userList = userRepository.findAll();
        return  userList;
    }

}

