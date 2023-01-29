package com.cakestation.backend.cakestore.controller;

import com.cakestation.backend.cakestore.controller.dto.response.CakeStoreResponse;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CakeStoreController 는")
public class CakeStoreControllerTest extends ControllerTest {
    @DisplayName("가게의 정보를 조회한다.")
    @Test

    public void getStoreByStoreId() throws Exception {
        CakeStoreDto cakeStoreDto = new CakeStoreDto(1L, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL,
                KAKAOMAP_URL, NEARBY_STATION, new ArrayList<>(), 0, 0);
        given(cakeStoreQueryService.findStoreById(anyLong())).willReturn(cakeStoreDto);

        CakeStoreResponse cakeStoreResponse = new CakeStoreResponse(1L, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL,
                KAKAOMAP_URL, NEARBY_STATION, new ArrayList<>(), 0, 0);

        ApiResponse<CakeStoreResponse> expectedResponse = new ApiResponse<>(
                HttpStatus.OK.value(), "가게 조회 성공", cakeStoreResponse);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/stores/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(expectedResponse))
                )
                .andDo(print())
                .andReturn();

    }
}
