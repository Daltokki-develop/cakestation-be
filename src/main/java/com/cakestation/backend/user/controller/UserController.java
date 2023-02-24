package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.auth.InvalidTokenException;
import com.cakestation.backend.common.dto.ApiResponse;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.common.config.JwtProperties;
import com.cakestation.backend.common.config.KakaoConfig;
import com.cakestation.backend.user.controller.dto.NicknameResponse;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UserService;
import com.cakestation.backend.common.auth.AuthUtil;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import com.cakestation.backend.user.service.dto.response.TokenDto;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

import static com.cakestation.backend.common.auth.AuthUtil.getCurrentUserEmail;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final KakaoService kakaoService;
    private final UserService userService;
    private final AuthUtil authUtil;
    private final KakaoConfig kakaoConfig;

    // code 를 통한 token 반환 API
    @GetMapping("/oauth")
    public ResponseEntity<ApiResponse<Long>> kakaoCallback(@RequestParam String code) {

        //Token 획득
        TokenDto tokenDto = kakaoService.getKaKaoAccessToken(code);
        KakaoUserDto userInfo = kakaoService.getUserInfo(tokenDto.getAccessToken());
        Long userId = userService.join(userInfo);

        //얻은 토큰을 통한 유저정보 조회 및 저장
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + tokenDto.getAccessToken());

        return new ResponseEntity<>(
                new ApiResponse<>(200, userId), httpHeaders, HttpStatus.OK);
    }

    //로그아웃 API
    //TODO : Cookie 에 있을 refresh Token 도 삭제 할것
    @PostMapping("/logout/kakao")
    public ResponseEntity kakaoLogout(@RequestHeader(JwtProperties.HEADER_STRING) String token) throws URISyntaxException {
        kakaoService.logoutToken(token); // Token 강제 만료

//        URI redirectUri = new URI(kakaoConfig.LOGOUT_URL);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(redirectUri);
//        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 회원탈퇴 API
    @PostMapping("/delete/kakao")
    public ResponseEntity<ApiResponse<String>> withdrawalUser(HttpServletRequest request) {
        kakaoService.deleteUser(authUtil.headerAccessToken(request, JwtProperties.HEADER_STRING).get()
                .replace(JwtProperties.TOKEN_PREFIX, ""));
        userService.deleteUser(authUtil.getCurrentUserEmail());
        return ResponseEntity.ok(new ApiResponse<>(200, "회원삭제 성공"));

    }

    // nickname 재발급 API
    @PatchMapping("/nickname")
    public ResponseEntity<NicknameResponse> updateNickname() {
        String email = getCurrentUserEmail();
        return ResponseEntity.ok(
                new NicknameResponse(userService.updateNickname(email)));
    }
}
