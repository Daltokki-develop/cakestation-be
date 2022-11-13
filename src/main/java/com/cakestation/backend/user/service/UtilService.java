package com.cakestation.backend.user.service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.cakestation.backend.common.handler.exception.EmailNotFoundException;
import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.config.JwtProperties;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import com.cakestation.backend.user.service.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UtilService {

    private final UserRepository userRepository;
    private final KakaoService kakaoService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public HashMap makeCookie(TokenDto tokenDto) {
        HashMap<String, Cookie> cookieMap = new HashMap<String, Cookie>();

        Cookie accessToken = new Cookie("Authorization", tokenDto.getAccessToken());
        Cookie refreshToken = new Cookie("refresh", tokenDto.getRefreshToken());

//        accessToken = new Cookie("Authorization", tokenDto.getAccessToken());
//        refreshToken = new Cookie("refresh", tokenDto.getRefreshToken());
        // ->토큰 만료시간과 동일하게 쿠키만료시간 설정하기
        accessToken.setMaxAge(tokenDto.getAccessExpires());
        accessToken.setPath("/");
//        accessToken.setPath("/localhost");

        refreshToken.setMaxAge(tokenDto.getRefreshExpires());
        refreshToken.setPath("/");
//        refreshToken.setPath("/localhost");


        cookieMap.put("access", accessToken);
        cookieMap.put("refresh", refreshToken);

        return cookieMap;
    }


    public Optional<String> headerAccessToken(HttpServletRequest request, String Target) {

        Optional<String> TokenValue = Optional.ofNullable(request.getHeader(Target));
        TokenValue.orElseThrow(() -> new IdNotFoundException("유저정보를 확인할 수 없습니다."));

        return TokenValue;
    }

    public String getCurrentUserEmail(String token) {
        try {
            String accessToken = token.replace(JwtProperties.TOKEN_PREFIX, "");
            KakaoUserDto userDto = kakaoService.getUserInfo(accessToken);
            return userDto.getEmail();
        } catch (Exception e) {
            throw new EmailNotFoundException("로그인이 필요한 서비스입니다.");
        }
    }
}
