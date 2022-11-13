package com.cakestation.backend.common.filter;


import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.config.JwtProperties;
import com.cakestation.backend.config.KakaoConfig;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    //사용하는 API 적기
    private static final String[] whiteList = {
            "test/**"
    };

    private final KakaoService kakaoService;

    public LoginFilter(KakaoService kakaoService, UtilService utilService, KakaoConfig kakaoConfig) {
        this.kakaoService = kakaoService;
    }

    //    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest httpreq = (HttpServletRequest) req;
        String AccessToken = httpreq.getHeader(JwtProperties.HEADER_STRING);
        String reqURI = httpreq.getRequestURI();

        try {
            if (CheckPath(reqURI)) {
                log.info("인증 체크 시작", reqURI);
                kakaoService.checkAccessToken(AccessToken);
            } else {
                log.info("검증은 없음");
                //kakao URL인증을 위해 모든 URL을 열어둬야함x
                chain.doFilter(req, res);
            }

        } catch (Exception e) {
            log.info("에러", e);
            throw new IdNotFoundException("접근 권한이 없습니다.");
        }

    }

    // whiteList와 URL이 같다면 인증체크를 시작함
    private boolean CheckPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

}
