package com.cakestation.backend.user.service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.cakestation.backend.common.handler.exception.EmailNotFoundException;
import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final KakaoService kakaoService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public HashMap makeCookie(TokenDto tokenDto){
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

    public String cookieAccessToken(HttpServletRequest request,String Target){

        String TokenValue = null;
        Optional<Cookie[]> cookies = Optional.ofNullable(request.getCookies());
        cookies.orElseThrow(()-> new IdNotFoundException("유저정보를 확인할 수 없습니다."));

        for(Cookie ele: cookies.get()){
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
           accessToken = this.cookieAccessToken(request, "Authorization");
        }
        else{
            throw new IdNotFoundException("사용자를 식별할 수 없습니다.");
        }

        try{
            KakaoUserDto userDto = kakaoService.getUserInfo(accessToken);
            return userDto.getEmail();
        }catch (Exception e){
            throw new EmailNotFoundException("로그인이 필요한 서비스입니다.");
        }
    }

    public int makeNickName(HttpServletRequest request){

        String resultname = null;
        Optional<User> checkNickname = null;

        String [] MBTI = {"ISTJ" , "ISTP" , "ISFJ" , "ISFP" , "INTJ" , "INTP" , "INFJ" , "INFP"
        , "ESTJ" , "ESTP" , "ESFJ" , "ESEP" ,"ENTJ" , "ENTP" , "ENFJ" , "ENFP"};
        String [] actions = {"포효하는 " , "울음많은 " , "화가많은 " , "고민많은 " , "소심한 " , "웃음많은 " , "어지러운 " ,
        "말이많은 " , "걱정많은 " , "친구많은 " , "친구없는 " , "차가운 " , "냉정한 " , "계획적인 "};

        Random random = new Random();

        resultname = actions[random.nextInt(actions.length)] + MBTI[random.nextInt(MBTI.length)];

        //닉네임 중복여부 확인하여 입력
        checkNickname = userRepository.findByNickname(resultname);


        //DB안에 같은 데이터가 있으면 계속 새로운 닉네임을 만들기
        while (checkNickname.isPresent()){
            resultname = actions[random.nextInt(actions.length)] + MBTI[random.nextInt(MBTI.length)];
            checkNickname = userRepository.findByNickname(resultname);
        }

        //쿠키를 통한 사용자 로직 조회
        String accessToken = this.cookieAccessToken(request, "Authorization");
        KakaoUserDto targetUser = kakaoService.getUserInfo(accessToken);

        //유저 닉네임 저장
        User saveUser = userRepository.findUserByEmail(targetUser.getEmail()).get();
        int result= userRepository.updateNickname(saveUser,resultname);


        return result;
    }
}
