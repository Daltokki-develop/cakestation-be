package com.cakestation.backend.cakestore.controller;

import com.cakestation.backend.cakestore.controller.dto.response.CakeStoreResponse;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.common.ControllerTest;
import com.cakestation.backend.common.dto.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CakeStoreController 는")
public class CakeStoreControllerTest extends ControllerTest {
    @DisplayName("가게의 정보를 조회한다.")
    @Test
    public void get_store_by_store_id() throws Exception {
        given(cakeStoreQueryService.findStoreById(anyLong())).willReturn(
                new CakeStoreDto(1L, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL,
                        KAKAOMAP_URL, NEARBY_STATION, new ArrayList<>(), 0, 0));

        CakeStoreResponse cakeStoreResponse = new CakeStoreResponse(1L, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL,
                KAKAOMAP_URL, NEARBY_STATION, new ArrayList<>(), 0, 0);

        ApiResponse<CakeStoreResponse> expectedResponse = new ApiResponse<>(
                HttpStatus.OK.value(), cakeStoreResponse);

        mockMvc.perform(
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

    @DisplayName("가게를 가게 이름으로 검색한다.")
    @Test
    public void get_store_by_store_name() throws Exception {
        CakeStoreDto STORE_1 = new CakeStoreDto(1L, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL,
                KAKAOMAP_URL, NEARBY_STATION, new ArrayList<>(), 0, 0);
        CakeStoreDto STORE_2 = new CakeStoreDto(2L, NAME_2, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL,
                KAKAOMAP_URL, NEARBY_STATION, new ArrayList<>(), 0, 0);

        given(cakeStoreQueryService.searchStoresByName(anyString(), any(PageRequest.class)))
                .willReturn(List.of(STORE_1, STORE_2));

        ApiResponse<List<CakeStoreResponse>> expectedResponse = new ApiResponse<>(
                HttpStatus.OK.value(), List.of(CakeStoreResponse.from(STORE_1), CakeStoreResponse.from(STORE_2)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/stores/search/store")
                                .queryParam("name", "케이크")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(expectedResponse))
                )
                .andDo(print())
                .andReturn();
    }

    @DisplayName("가게를 지하철 역이름으로 검색한다.")
    @Test
    public void get_store_by_station_name() throws Exception {
        CakeStoreDto STORE_1 = new CakeStoreDto(1L, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL,
                KAKAOMAP_URL, NEARBY_STATION, new ArrayList<>(), 0, 0);
        CakeStoreDto STORE_2 = new CakeStoreDto(2L, NAME_2, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL,
                KAKAOMAP_URL, NEARBY_STATION, new ArrayList<>(), 0, 0);

        given(cakeStoreQueryService.searchStoresByStation(anyString(), any(PageRequest.class)))
                .willReturn(List.of(STORE_1, STORE_2));

        ApiResponse<List<CakeStoreResponse>> expectedResponse = new ApiResponse<>(
                HttpStatus.OK.value(), List.of(CakeStoreResponse.from(STORE_1), CakeStoreResponse.from(STORE_2)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/stores/search/station")
                                .queryParam("name", "강남역")
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
