package com.cakestation.backend.subway.dto.response;

import com.cakestation.backend.subway.domain.Subway;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class SubwayResponse {

    private Long id;
    private String line;
    private String station;
    private Double longitude;
    private Double latitude;

    public static SubwayResponse from(Subway subway) {
        return SubwayResponse.builder()
                .id(subway.getId())
                .line(subway.getLine())
                .station(subway.getStation())
                .longitude(subway.getLongitude())
                .latitude(subway.getLatitude())
                .build();
    }
}
