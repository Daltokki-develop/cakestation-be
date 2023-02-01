package com.cakestation.backend.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoConfig implements ApplicationListener<ApplicationStartedEvent> {

    @Value("${kakao.key}")
    private String key;
    @Value("${cloud.aws.ip}")
    private String ip;
    //회원정보 조회시 사용하는 URL
    public static String GET_USER_URL = "https://kapi.kakao.com/v2/user/me";
    //로그아웃을 통해 발급된 토큰 만료시 사용
    //토큰 유효성겁사시 사용
    public static String CHECK_TOKEN = "https://kapi.kakao.com/v1/user/access_token_info";
    //회원탈퇴용 URL
    public static String WITH_DRAWL_USER = "https://kapi.kakao.com/v1/user/unlink";
    public static String LOGOUT_REDIRECT_URI = "http://127.0.0.1:3000";
    // 로그아웃 URL
    public String LOGOUT_URL;
    public String REDIRECT_URI;
    //Token 회득시 사용되는 URL
    public String GET_TOKEN_URL;
    //LoginPage 로 redirect 될 시 사용되는 URL
    public String REDIRECT_LOGINPAGE;
    //refreshToken 을 통한 토큰 갱신
    public String REFRESH_ACCESS;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        REDIRECT_URI = "http://localhost:3000/login/oauth/";
        LOGOUT_URL = "https://kauth.kakao.com/oauth/logout?client_id=" + key + "&logout_redirect_uri=" + LOGOUT_REDIRECT_URI;
        GET_TOKEN_URL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=" + key;
        REDIRECT_LOGINPAGE = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" +
                key +
                "&redirect_uri=" + REDIRECT_URI;
        REFRESH_ACCESS = "https://kauth.kakao.com/oauth/token?grant_type=refresh_token&client_id=" +
                key +
                "&refresh_token=";
    }
}