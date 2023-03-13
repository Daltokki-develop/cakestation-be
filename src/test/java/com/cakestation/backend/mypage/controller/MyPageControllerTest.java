package com.cakestation.backend.mypage.controller;

import com.cakestation.backend.common.ControllerTest;
import com.cakestation.backend.common.dto.ApiResponse;
import com.cakestation.backend.mypage.service.dto.MyPageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.cakestation.backend.common.auth.AuthUtil.getCurrentUserEmail;
import static com.cakestation.backend.user.fixture.UserFixture.EMAIL;
import static com.cakestation.backend.user.fixture.UserFixture.NICKNAME;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MyPageController 는")
public class MyPageControllerTest extends ControllerTest {

    @DisplayName("마이페이지 정보를 조회할 수 있다.")
    @Test
    public void get_my_page_info() throws Exception {

        MyPageDto myPageDto = new MyPageDto(NICKNAME, 1, 2, 3, 4);
        given(getCurrentUserEmail()).willReturn(EMAIL);
        given(myPageService.getMyPageInfo(anyString())).willReturn(myPageDto);

        ApiResponse<MyPageDto> expectedResponse = new ApiResponse<>(HttpStatus.OK.value(), myPageDto);

        String uri = "/api/mypage";

        mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .header("Authorization", "abc")
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(expectedResponse))
                )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
