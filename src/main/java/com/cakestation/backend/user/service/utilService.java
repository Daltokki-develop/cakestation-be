package com.cakestation.backend.user.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.Cookie;

import com.cakestation.backend.user.dto.response.TokenDto;


public class UtilService {

    public HashMap makeCookie(TokenDto tokenDto){
        HashMap<String, Cookie> cookieMap = new HashMap<String,Cookie>();

        Cookie accessToken = new Cookie("Authorization", tokenDto.getAccessToken());
        Cookie refreshToken = new Cookie("refresh", tokenDto.getRefreshToken());

        // ->토큰 만료시간과 동일하게 쿠키만료시간 설정하기
        accessToken.setMaxAge(tokenDto.getAccessExpires());
        accessToken.setPath("/");
        refreshToken.setMaxAge(tokenDto.getRefreshExpires());
        refreshToken.setPath("/");
        
        cookieMap.put("access", accessToken);
        cookieMap.put("refresh", refreshToken);        
        
        return cookieMap;
    }

    public String  CookieAccessToken(Cookie[] cookies , String Target){
        String TokenValue = null;

        for(Cookie ele: cookies){
            if(Target.equals(ele.getName())){
                TokenValue = ele.getValue();
            }
        }
        
        return TokenValue;
    }

}
