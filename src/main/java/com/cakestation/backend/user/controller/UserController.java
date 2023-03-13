package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.auth.AuthUtil;
import com.cakestation.backend.common.config.JwtProperties;
import com.cakestation.backend.common.config.KakaoConfig;
import com.cakestation.backend.common.dto.ApiResponse;
import com.cakestation.backend.user.controller.dto.NicknameResponse;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UserService;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import com.cakestation.backend.user.service.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

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

        TokenDto tokenDto = kakaoService.getKaKaoAccessToken(code);
        KakaoUserDto userInfo = kakaoService.getUserInfo(tokenDto.getAccessToken());
        Long userId = userService.join(userInfo);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + tokenDto.getAccessToken());

        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), userId), httpHeaders, HttpStatus.OK);
    }

    //로그아웃 API
    @PostMapping("/logout/kakao")
    public ResponseEntity<Void> kakaoLogout(@RequestHeader(JwtProperties.HEADER_STRING) String token) throws URISyntaxException {
        kakaoService.logoutToken(token); // Token 강제 만료

        return ResponseEntity.noContent().build();
    }

    // 회원탈퇴 API
    @PostMapping("/delete/kakao")
    public ResponseEntity<Void> withdrawalUser(HttpServletRequest request) {
        kakaoService.deleteUser(authUtil.headerAccessToken(request, JwtProperties.HEADER_STRING).get()
                .replace(JwtProperties.TOKEN_PREFIX, ""));
        userService.deleteUser(getCurrentUserEmail());

        return ResponseEntity.noContent().build();
    }

    // nickname 재발급 API
    @PatchMapping("/nickname")
    public ResponseEntity<NicknameResponse> updateNickname() {
        String email = getCurrentUserEmail();

        return ResponseEntity.ok(new NicknameResponse(userService.updateNickname(email)));
    }
}
