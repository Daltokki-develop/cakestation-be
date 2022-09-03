package com.cakestation.backend.user.service;

import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.cakestation.backend.common.handler.exception.EmailNotFoundException;
import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import com.cakestation.backend.user.service.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilService {

    private final KakaoService kakaoService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public static HashMap makeCookie(TokenDto tokenDto){
        HashMap<String, Cookie> cookieMap = new HashMap<String,Cookie>();

        Cookie accessToken = new Cookie("Authorization", tokenDto.getAccessToken());
        Cookie refreshToken = new Cookie("refresh", tokenDto.getRefreshToken());
    
//        accessToken = new Cookie("Authorization", tokenDto.getAccessToken());
//        refreshToken = new Cookie("refresh", tokenDto.getRefreshToken());
        // ->토큰 만료시간과 동일하게 쿠키만료시간 설정하기
        accessToken.setMaxAge(tokenDto.getAccessExpires());
        accessToken.setPath("/");
        refreshToken.setMaxAge(tokenDto.getRefreshExpires());
        refreshToken.setPath("/");
        
        cookieMap.put("access", accessToken);
        cookieMap.put("refresh", refreshToken);        
        
        return cookieMap;
    }

    public String cookieAccessToken(Cookie[] cookies , String Target){
        String TokenValue = null;

        for(Cookie ele: cookies){
            if(Target.equals(ele.getName())){
                TokenValue = ele.getValue();
            }
        }
        
        return TokenValue;
    }

    public String getCurrentUserEmail(HttpServletRequest request){

        String accessToken=null;
        Cookie [] cookies = request.getCookies();
        if(cookies != null){
           accessToken = this.cookieAccessToken(cookies, "Authorization");
        }

        try{
            KakaoUserDto userDto = kakaoService.getUserInfo(accessToken);
            return userDto.getEmail();
        }catch (Exception e){
            throw new EmailNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}
