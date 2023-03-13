package com.cakestation.backend.mypage.controller;

import com.cakestation.backend.common.dto.ApiResponse;
import com.cakestation.backend.mypage.service.MyPageService;
import com.cakestation.backend.mypage.service.dto.MyPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.cakestation.backend.common.auth.AuthUtil.getCurrentUserEmail;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<MyPageDto>> getMyPage() {
        MyPageDto myPageInfo = myPageService.getMyPageInfo(getCurrentUserEmail());

        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), myPageInfo));
    }
}
