//package com.cakestation.backend.user.controller;
//
//
//import com.cakestation.backend.user.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static com.cakestation.backend.user.fixture.UserFixture.getKakaoUserDto;
//
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
//public class UserControllerTest {
//
//        @Autowired
//        private MockMvc mockMvc;
//
//        @Autowired
//        private UserService userService;
//
//        @Test
//        public void User_nickname_등록() throws Exception {
//                // given
//                // 회원등록
//                userService.join(getKakaoUserDto());
//
//                String uri = String.format("/api/nickname");
//
////                MvcResult result = mockMvc.perform(
////                        MockMvcRequestBuilders.patch(uri)
////                                .accept(MediaType.APPLICATION_JSON)
////                )
////                        .andExpect(status().isOk())
////                        .andDo(MockMvcResultHandlers.print())
////                        .andReturn();
//        }
//}
