package com.cakestation.backend.common.auth;


import com.cakestation.backend.common.domain.CustomUserDetails;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.common.config.JwtProperties;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.KakaoService;
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
public class AuthorizationFilter implements Filter {

    // 인증을 적용하지 않을 API 작성
    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    private static final String[] whiteList = {
            "/api/oauth", "/api/subway/**"
    };

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        String reqURI = httpReq.getRequestURI();
        if (!checkPath(reqURI)) {
            String accessToken = httpReq.getHeader(JwtProperties.HEADER_STRING);
            validateTokenExists(accessToken);

            log.info("토큰 검증 시작");
            kakaoService.checkAccessToken(accessToken);
            User user = getUser(kakaoService.getUserInfo(accessToken).getEmail());
            log.info("토큰 검증 완료");

            setSecurityContext(user);
        } else {
            log.info("검증 없음");
        }
        chain.doFilter(req, res);
        log.info("Authorization Filter 통과");
    }

    private void setSecurityContext(User user) {
        CustomUserDetails principalDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails, "", principalDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
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
