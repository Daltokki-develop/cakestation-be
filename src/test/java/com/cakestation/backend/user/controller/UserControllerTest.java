package com.cakestation.backend.user.controller;

import com.cakestation.backend.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.cakestation.backend.common.auth.AuthUtil.getCurrentUserEmail;
import static com.cakestation.backend.user.fixture.UserFixture.EMAIL;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("UserController 는")
public class UserControllerTest extends ControllerTest {

    @DisplayName("닉네임을 업데이트할 수 있다.")
    @Test
    public void update_nickname() throws Exception {
        // given
        given(getCurrentUserEmail()).willReturn(EMAIL);
        given(userService.updateNickname(anyString())).willReturn("SongSong");
        String uri = "/api/nickname";

        mockMvc.perform(
                        MockMvcRequestBuilders.patch(uri)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .header("Authorization", "abc")
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
