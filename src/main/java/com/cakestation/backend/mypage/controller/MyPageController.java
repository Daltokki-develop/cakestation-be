package com.cakestation.backend.mypage.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.mypage.service.MyPageService;
import com.cakestation.backend.mypage.service.dto.MyPageDto;
import com.cakestation.backend.user.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {

    private final MyPageService myPageService;
    private final UtilService utilService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<MyPageDto>> getMyPage(HttpServletRequest req){
//        String email = utilService.getCurrentUserEmail(req);

        MyPageDto myPageInfo = myPageService.getMyPageInfo("ksl2950@naver.com");
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "마이 페이지 조회 성공",myPageInfo));
    }
}
