package com.cakestation.backend.common.filter;


import com.cakestation.backend.auth.exception.InvalidTokenException;
import com.cakestation.backend.common.CustomUserDetails;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.config.JwtProperties;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.dto.response.CheckDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter implements Filter {

    // 인증을 적용하지 않을 API 작성
    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    private static final String[] whiteList = {
            "/api/oauth", "/api/subway/**"
    };

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        String reqURI = httpReq.getRequestURI();
        if (!checkPath(reqURI)) {
            String accessToken = httpReq.getHeader(JwtProperties.HEADER_STRING);
            validateTokenExists(accessToken);

            log.info("토큰 검증 시작");
            CheckDto checkDto = kakaoService.checkAccessToken(accessToken);
            User user = getUser(kakaoService.getUserInfo(accessToken).getEmail());
            log.info("토큰 검증 완료");

            CustomUserDetails principalDetails = new CustomUserDetails(user);

            // jwt 토큰 서명이 정상이면 Authentication 객체 만듦
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails, "", principalDetails.getAuthorities());

            // 강제로 시큐리티 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(req, res);
        } else {
            log.info("검증은 없음");
            chain.doFilter(req, res);
        }

    }

    private User getUser(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));
    }

    private void validateTokenExists(String accessToken) {
        if (accessToken.isEmpty()) {
            throw new InvalidTokenException(ErrorType.INVALID_TOKEN);
        }
    }

    // WhiteList 와 URL 이 같다면 인증체크를 미적용
    private boolean checkPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

}
