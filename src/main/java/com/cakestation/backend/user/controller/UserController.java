package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.config.JwtProperties;
import com.cakestation.backend.user.controller.dto.NicknameResponse;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UserService;
import com.cakestation.backend.user.service.UtilService;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import com.cakestation.backend.user.service.dto.response.CheckDto;
import com.cakestation.backend.user.service.dto.response.TokenDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final KakaoService kakaoService;
    private final UserService userService;
    private final UtilService utilService;

    @GetMapping("/email")
    public ResponseEntity<ApiResponse<String>> getEmail(@RequestHeader("Authorization") String token) {

        String email = utilService.getCurrentUserEmail(token);
        return new ResponseEntity<>(new ApiResponse<>(200, "이메일 획득", email), HttpStatus.OK);
    }

    // code를 통한 Token 반환 API
    @GetMapping("/oauth")
    public ResponseEntity<ApiResponse<Long>> KakaoCallback(@RequestParam String code) {

        //Token 획득
        TokenDto tokenDto = kakaoService.getKaKaoAccessToken(code);
        KakaoUserDto userInfo = kakaoService.getUserInfo(tokenDto.getAccessToken());
        Long userId = userService.join(userInfo);

        //얻은 토큰을 통한 유저정보 조회 및 저장
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + tokenDto.getAccessToken());

        return new ResponseEntity<>(
                new ApiResponse<>(200, "로그인 성공", userId), httpHeaders, HttpStatus.OK);
    }

    //로그아웃 API
    //TODO : Cookie에 있을 refresh Token도 삭제 할것
    @GetMapping("/logout/kakao")
    public ResponseEntity<ApiResponse<String>> KakaoLogout(HttpServletRequest request) {

        Optional<String> Token = utilService.headerAccessToken(request, JwtProperties.HEADER_STRING);
        String userEmail = utilService.getCurrentUserEmail(Token.get());
        kakaoService.LogoutToken(Token.get().replace(JwtProperties.TOKEN_PREFIX, ""));//Token 강제 만료
        return ResponseEntity.ok(new ApiResponse<>(200, "로그아웃 성공", userEmail));

    }

    // 회원탈퇴 API
    @PostMapping("/delete/kakao")
    public ResponseEntity<ApiResponse<String>> WithdrawalUser(HttpServletRequest request) {

        Optional<String> Token = utilService.headerAccessToken(request, JwtProperties.HEADER_STRING);
        String userEmail = utilService.getCurrentUserEmail(Token.get());
        kakaoService.deleteUser(Token.get().replace(JwtProperties.TOKEN_PREFIX, "")); //Kakao에 등록된 유저정보 삭제
        userService.deleteUser(userEmail); // DB에 저장된 회원정보 삭제
        return ResponseEntity.ok(new ApiResponse<>(200, "회원삭제 성공", userEmail));
    }

    // nickname 재발급 API
    @PatchMapping("/nickname")
    public ResponseEntity<NicknameResponse> updateNickname(@RequestHeader(JwtProperties.HEADER_STRING) String token) {
        String email = utilService.getCurrentUserEmail(token);
        System.out.println(email + "!!!!!!!");
        return ResponseEntity.ok(
                new NicknameResponse(userService.updateNickname(email)));
    }
}
