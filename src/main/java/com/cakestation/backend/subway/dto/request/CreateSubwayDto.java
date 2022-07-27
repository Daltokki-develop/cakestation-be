package com.cakestation.backend.subway.dto.request;

import com.cakestation.backend.subway.domain.Subway;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CreateSubwayDto {
    private Long subway_id;
    private String line;
    private String station;
    private Double longitude;
    private Double latitude;

    public static Subway toEntity(CreateSubwayDto createSubwayDto) {
        return Subway.builder()
                .id(createSubwayDto.getSubway_id())
                .line(createSubwayDto.getLine())
                .station(createSubwayDto.getStation())
                .longitude(createSubwayDto.getLongitude())
                .latitude(createSubwayDto.getLatitude())
                .build();
    }
}
