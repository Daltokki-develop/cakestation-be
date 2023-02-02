package com.cakestation.backend.subway.controller;

import com.cakestation.backend.subway.fixture.SubwayFixture;
import com.cakestation.backend.subway.dto.request.CreateSubwayDto;
import com.cakestation.backend.subway.service.SubwayService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class SubwayControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SubwayService subwayService;

    @DisplayName("지하철역을 전체 조회할 수 있다.")
    @Test
    public void getSubway() throws Exception {

        CreateSubwayDto createSubwayDto = SubwayFixture.getSubwayDto();
        subwayService.saveSubway(createSubwayDto);
        String uri = "/api/subway/all";

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}