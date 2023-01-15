package com.cakestation.backend.common.filter;


import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.config.JwtProperties;
import com.cakestation.backend.config.KakaoConfig;
import com.cakestation.backend.user.exception.InvalidUserException;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UtilService;
import com.cakestation.backend.user.service.dto.response.CheckDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.OptionalInt;

@Slf4j
public class LoginFilter implements Filter {

    // 인증을 적용하지 않을 API 작성
    private static final String[] whiteList = {
            "/api/oauth", "/api/subway/**"
    };

    private final KakaoService kakaoService;

    public LoginFilter(KakaoService kakaoService, UtilService utilService, KakaoConfig kakaoConfig) {
        this.kakaoService = kakaoService;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpreq = (HttpServletRequest) req;
        String AccessToken = httpreq.getHeader(JwtProperties.HEADER_STRING);
        String reqURI = httpreq.getRequestURI();

        if (!CheckPath(reqURI)) {
            log.info("인증 체크 시작", reqURI);
            Optional.ofNullable(kakaoService.checkAccessToken(AccessToken))
                    .orElseThrow(() -> new InvalidUserException(ErrorType.INVALID_TOKEN));
        } else {
            log.info("검증은 없음");
            //kakao URL인증을 위해 모든 URL을 열어둬야함x
            chain.doFilter(req, res);
        }

    }

    // whiteList와 URL이 같다면 인증체크를 미적용
    private boolean CheckPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

}
