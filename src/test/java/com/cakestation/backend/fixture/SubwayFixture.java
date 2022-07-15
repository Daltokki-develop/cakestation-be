package com.cakestation.backend.fixture;

import com.cakestation.backend.domain.Subway;
import com.cakestation.backend.service.dto.request.CreateSubwayDto;

public class SubwayFixture {

    public static Long SUBWAY_ID = 1L;
    public static String LINE = "1호선";
    public static String STATION = "대전역";
    public static Double LONGITUDE = 12.2131;
    public static final Double LATITUDE = 34.2345;

    public static CreateSubwayDto getSubwayDto(){
        return CreateSubwayDto.builder()
                .subway_id(SUBWAY_ID)
                .line(LINE)
                .station(STATION)
                .longitude(LONGITUDE)
                .latitude(LATITUDE)
                .build();
    }
}
