package com.cakestation.backend.subway.service;

import com.cakestation.backend.subway.domain.Subway;
import com.cakestation.backend.subway.repository.SubwayRepository;
import com.cakestation.backend.subway.service.SubwayService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.cakestation.backend.subway.fixture.SubwayFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubwayServiceUnitTest {

    @Mock
    SubwayRepository subwayRepository;

    @InjectMocks
    SubwayService subwayService;

    @Test
    public void 지하철역_전체조회() {
        //given
        List<Subway> subways = new ArrayList<>();
        Subway subway = Subway.builder()
                .id(SUBWAY_ID)
                .line(LINE)
                .station(STATION)
                .longitude(LONGITUDE)
                .latitude(LATITUDE)
                .build();

        subways.add(subway);
        when(subwayRepository.findAll()).thenReturn(subways); // mock 객체 주입

        //when
        List<Subway> subwayList = subwayService.findAll();

        //then
        assertEquals(subways.get(0).getStation(),subwayList.get(0).getStation());
    }
}