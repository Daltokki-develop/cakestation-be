package com.cakestation.backend.subway.controller;

import com.cakestation.backend.subway.fixture.SubwayFixture;
import com.cakestation.backend.subway.dto.request.CreateSubwayDto;
import com.cakestation.backend.subway.service.SubwayService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class SubwayControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SubwayService subwayService;

    @Test
    public void 지하철_전체조회() throws Exception {

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