package com.cakestation.backend.service;

import com.cakestation.backend.domain.Subway;
import com.cakestation.backend.repository.SubwayRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubwayServiceTest {

    @Mock
    SubwayRepository subwayRepository;

    @InjectMocks
    SubwayService subwayService;

    @Test
    public void 지하철역_전체조회() {
        //given
        List<Subway> subways = new ArrayList<>();
        Subway subway = Subway.builder()
                .id(1L)
                .line("1호선")
                .station("희룡")
                .longitude(127.046895)
                .latitude(37.724846)
                .build();
        subways.add(subway);
        when(subwayRepository.findAll()).thenReturn(subways); // mock 객체 주입

        //when
        List<Subway> subwayList = subwayService.findAll();

        //then
        assertEquals(subways.get(0).getStation(),subwayList.get(0).getStation());
    }
}