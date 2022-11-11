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

    public HashMap makeCookie(TokenDto tokenDto){
        HashMap<String, Cookie> cookieMap = new HashMap<String,Cookie>();

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

//    public String cookieAccessToken(HttpServletRequest request,String Target){
//        log.info("인증 체크 시작");
//        String TokenValue = null;
//        Optional<Cookie[]> cookies = Optional.ofNullable(request.getCookies());
//        cookies.orElseThrow(()-> new IdNotFoundException("유저정보를 확인할 수 없습니다."));
//
//        for(Cookie ele: cookies.get()){
//            if(Target.equals(ele.getName())){
//                TokenValue = ele.getValue();
//            }
//        }
//
//        return TokenValue;
//    }

    public Optional<String> headerAccessToken(HttpServletRequest request, String Target){

        Optional<String> TokenValue = Optional.ofNullable(request.getHeader(Target));
        TokenValue.orElseThrow(()-> new IdNotFoundException("유저정보를 확인할 수 없습니다."));

        return TokenValue;
    }

    public String getCurrentUserEmail(String token){
        try{
            String accessToken = token.replace("Bearer ", "");
            KakaoUserDto userDto = kakaoService.getUserInfo(accessToken);
            return userDto.getEmail();
        }catch (Exception e){
            throw new EmailNotFoundException("로그인이 필요한 서비스입니다.");
        }
    }

    public Optional<User> getUserinfo(String email){

        Optional<User> user = userRepository.findUserByEmail(email);

        user.orElseThrow(() -> new IdNotFoundException("유저정보를 확인할 수 없습니다."));

        return user;
    }

    public void makeNickName(String userEmail , boolean reNickname) {

        String resultname = null;
        Optional<User> checkNickname = null;

        Optional<User> user = userRepository.findUserByEmail(userEmail);
        user.orElseThrow(() -> new IdNotFoundException("찾을수 없는 유저입니다."));

        if (user.get().getNickname() == null || reNickname) {
            String[] MBTI = {"체리", "망고", "복숭아", "오렌지", "사과", "바나나", "두리안", "메론"
                    , "수박", "딸기", "망고스틴", "키위", "올리브", "참외", "블루베리", "파인애플", "딸기"
                    , "라즈베리", "두리안", "청사과"};
            String[] actions = {"포효하는 ", "울음많은 ", "화가많은 ", "고민많은 ", "소심한 ", "웃음많은 ", "어지러운 ",
                    "말이많은 ", "걱정많은 ", "친구많은 ", "친구없는 ", "차가운 ", "냉정한 ", "계획적인 "};

            Random random = new Random();

            resultname = actions[random.nextInt(actions.length)] + MBTI[random.nextInt(MBTI.length)];

            //닉네임 중복여부 확인하여 입력
            checkNickname = userRepository.findByNickname(resultname);

            //DB안에 같은 데이터가 있으면 계속 새로운 닉네임을 만들기
            while (checkNickname.isPresent()) {
                resultname = actions[random.nextInt(actions.length)] + MBTI[random.nextInt(MBTI.length)];
                checkNickname = userRepository.findByNickname(resultname);
            }

            //유저 닉네임 저장
            int result = userRepository.updateNickname(user.get(), resultname);

        }

    }
}
