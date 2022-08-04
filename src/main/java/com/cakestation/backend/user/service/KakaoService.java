package com.cakestation.backend.user.service;

import com.cakestation.backend.user.dto.response.KakaoUserDto;
import com.cakestation.backend.user.dto.response.TokenDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.cakestation.backend.config.KakaoConfig.REDIRECT_URI;
import static com.cakestation.backend.config.KakaoConfig.REST_API_KEY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoService {

    public TokenDto getKaKaoAccessToken(String code) {
        TokenDto tokenDto = null;

        try {
            URL url = new URL("https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=" + REST_API_KEY + "&redirect_uri=" + REDIRECT_URI + "&code=" + code);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            bw.write(sb.toString());

            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            tokenDto = TokenDto.builder()
                    .accessToken(element.getAsJsonObject().get("access_token").getAsString())
                    .refreshToken(element.getAsJsonObject().get("refresh_token").getAsString())
                    .build();

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokenDto;
    }

    public KakaoUserDto getUserInfo(String access_Token) {

        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        KakaoUserDto kakaoUserDto = null;

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            System.out.println("Element" + element);
            JsonObject account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String username = account.getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();
            String email = account.getAsJsonObject().get("email").getAsString();
            
            kakaoUserDto = KakaoUserDto.builder()
                    .username(username)
                    .email(email)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return kakaoUserDto;
    }
}
