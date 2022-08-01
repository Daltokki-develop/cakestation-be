package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.config.KakaoConfig;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UserService;
import com.cakestation.backend.user.dto.response.KakaoUserDto;
import com.cakestation.backend.user.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import static com.cakestation.backend.config.KakaoConfig.REDIRECT_URI;
import static com.cakestation.backend.config.KakaoConfig.REST_API_KEY;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final KakaoService kakaoService;
    private final UserService userService;

    //    @GetMapping("/login")
//    public  void loginAction() throws IOException {
//        URL url = new URL("https://kauth.kakao.com/oauth/authorize");
////      //?response_type=code&client_id="+ KakaoConfig.REST_API_KEY +"uri="+KakaoConfig.REDIRECT_URI
//        HttpURLConnection con = (HttpURLConnection)url.openConnection();
//        con.setRequestMethod("GET");
//        con.setDoOutput(true);
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
//        StringBuilder sb = new StringBuilder();
//        sb.append("response_type=code");
//        sb.append("&client_id=" + REST_API_KEY); // TODO REST_API_KEY 입력
//        sb.append("&uri=" + REDIRECT_URI); // TODO 인가코드 받은 redirect_uri 입력
//        bw.write(sb.toString());
//        bw.flush();
//
//        int responseCode = con.getResponseCode();
//        System.out.println("responseCode :::: " + responseCode);
//        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        System.out.println("result :::: " + br.readLine());
//    }
    @RequestMapping("/login")
    public RedirectView exRedirect4() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+ KakaoConfig.REST_API_KEY +"&redirect_uri="+KakaoConfig.REDIRECT_URI);
        return redirectView;
    }
    //인가 코드 반환
    @GetMapping("/oauth/kakao")
    public ResponseEntity KakaoCallback(@RequestParam String code, HttpServletResponse response) {
        TokenDto tokenDto = kakaoService.getKaKaoAccessToken(code);
        KakaoUserDto kakaoUserDto = kakaoService.getUserInfo(tokenDto.getAccessToken());

        //톰캣의 쿠키규칙으로 공백을 포함할수 없어서 utf-8로 인코딩을 해야함
        String cookieValue;
        try {
            // Bearer 정책을 활용하여 중간에 띄어쓰기를 위해 인코딩을하니 공백이 +로 변경되는 문제 발생
            cookieValue = URLEncoder.encode("Bearer " + tokenDto.getAccessToken(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Cookie setCookie = new Cookie("Authorization", cookieValue);
        //쿠키 시간을 하루로 지정(60초*60분*24시간)
        setCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(setCookie);

        Long userId = userService.join(kakaoUserDto);
        return new ResponseEntity<>(new ApiResponse(200, "로그인 성공", kakaoUserDto), HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout/kakao")
    public ResponseEntity logout(){
        return ResponseEntity.ok().build();
    }

    private final UserRepository userRepository;
    @GetMapping("/userinfo")
    @ResponseBody
    public List<User> userInfo(){
        List<User> userList = userRepository.findAll();
        return  userList;
    }

}

