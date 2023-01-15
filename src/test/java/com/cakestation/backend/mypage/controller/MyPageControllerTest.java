//package com.cakestation.backend.mypage.controller;
//
//import com.cakestation.backend.mypage.service.MyPageService;
//import com.cakestation.backend.mypage.service.dto.MyPageDto;
//import com.cakestation.backend.review.repository.ReviewRepository;
//import com.cakestation.backend.user.repository.UserRepository;
//import com.cakestation.backend.user.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//
//import java.util.Optional;
//
//import static com.cakestation.backend.user.fixture.UserFixture.getKakaoUserDto;
//import static com.cakestation.backend.user.fixture.UserFixture.getUserEntity;
//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.isNotNull;
//import static org.mockito.Mockito.doReturn;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
//public class MyPageControllerTest {
//
//        @Autowired
//        private MockMvc mockMvc;
//        @Autowired
//        UserService userService;
//        @InjectMocks
//        MyPageService myPageService;
//
//        @Test
//        public void 마이페이지_정보_조회() throws Exception {
//            // given
//            // 회원등록
//            userService.join(getKakaoUserDto());
//
//            MyPageDto myPageInfo = myPageService.getMyPageInfo(getKakaoUserDto().getEmail());
//
//            String uri = String.format("/api/mypage");
//
//            MvcResult result = mockMvc.perform(
//                    MockMvcRequestBuilders.get(uri)
//                            .accept(MediaType.APPLICATION_JSON)
//            )
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.nickname").isNotEmpty())
//                    .andExpect(jsonPath("$.reviewCount").isNumber()) // response값을 확인해보려고 했는데... 그냥 아는것만 할까요?? ㅜㅜ
//                    .andDo(MockMvcResultHandlers.print()) //지정하지 않으면 기본적으로 표준 출력(System.out.)이 출력 대상이 된다.
//                    .andReturn();
//        }
//
//}
