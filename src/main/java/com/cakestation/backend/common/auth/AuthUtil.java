package com.cakestation.backend.common.auth;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.common.config.JwtProperties;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final KakaoService kakaoService;

    public Optional<String> headerAccessToken(HttpServletRequest request, String Target) {

        Optional<String> TokenValue = Optional.ofNullable(request.getHeader(Target));
        TokenValue.orElseThrow(() -> new InvalidTokenException(ErrorType.INVALID_TOKEN));

        return TokenValue;
    }

    public String getCurrentUserEmailWithKApi(String token) {
        try {
            String accessToken = token.replace(JwtProperties.TOKEN_PREFIX, "");
            KakaoUserDto userDto = kakaoService.getUserInfo(accessToken);
            return userDto.getEmail();
        } catch (Exception e) {
            throw new InvalidTokenException(ErrorType.INVALID_TOKEN);
        }
    }

    public static String getCurrentUserEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context 에 인증 정보가 없습니다.");
            return null;
        }

        String email = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            email = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            email = (String) authentication.getPrincipal();
        }

        return email;
    }

//    public HashMap makeCookie(TokenDto tokenDto) {
//        HashMap<String, Cookie> cookieMap = new HashMap<String, Cookie>();
//
//        Cookie accessToken = new Cookie("Authorization", tokenDto.getAccessToken());
//        Cookie refreshToken = new Cookie("refresh", tokenDto.getRefreshToken());
//
////        accessToken = new Cookie("Authorization", tokenDto.getAccessToken());
////        refreshToken = new Cookie("refresh", tokenDto.getRefreshToken());
//        // ->토큰 만료시간과 동일하게 쿠키만료시간 설정하기
//        accessToken.setMaxAge(tokenDto.getAccessExpires());
//        accessToken.setPath("/");
////        accessToken.setPath("/localhost");
//
//        refreshToken.setMaxAge(tokenDto.getRefreshExpires());
//        refreshToken.setPath("/");
////        refreshToken.setPath("/localhost");
//
//
//        cookieMap.put("access", accessToken);
//        cookieMap.put("refresh", refreshToken);
//
//        return cookieMap;
//    }
}
