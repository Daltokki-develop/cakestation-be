package com.cakestation.backend.common.Filter;


import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.config.KakaoConfig;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    //사용하는 API 적기
    private static final String[] whiteList = {
            "stores/**"
            };

    private final KakaoService kakaoService;

    private final UtilService utilService;

    private final KakaoConfig kakaoConfig;

    public LoginFilter(KakaoService kakaoService, UtilService utilService, KakaoConfig kakaoConfig) {
        this.kakaoService = kakaoService;
        this.utilService = utilService;
        this.kakaoConfig = kakaoConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException , ServletException{


        HttpServletRequest httpreq = (HttpServletRequest) req;
        String reqURI = httpreq.getRequestURI();
        HttpServletResponse httpres = (HttpServletResponse) res;
        System.out.println(CheckPath(reqURI));
        System.out.println(reqURI);
        try{
            if(CheckPath(reqURI)){
                log.info("인증체크 시작" , reqURI);
                String findCookie = utilService.cookieAccessToken(httpreq, "Authorization");
                kakaoService.checkAccessToken(findCookie);
            }
            else {
                //kakao URL인증을 위해 모든 URL을 열어둬야함
                chain.doFilter(req,res);
            }

        }
        catch (Exception e) {
            throw new IdNotFoundException("접근 권한이 없습니다.");
        }

     }

     // whiteList와 URL이 같다면 인증체크를 시작함
     private boolean CheckPath(String requestURI){
        return PatternMatchUtils.simpleMatch(whiteList , requestURI);
    }

}
