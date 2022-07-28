package com.cakestation.backend.subway.dto.response;

import com.cakestation.backend.subway.domain.Subway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SubwayResponse {

    private Long id;
    private String line;
    private String station;
    private Double longitude;
    private Double latitude;

    public SubwayResponse(Subway subway){
        this.id = subway.getId();
        this.line = subway.getLine();
        this.station = subway.getStation();
        this.longitude = subway.getLongitude();
        this.latitude = subway.getLatitude();
    }
//    public static SubwayResponse from(Subway subway) {
//        return SubwayResponse.builder()
//                .id(subway.getId())
//                .line(subway.getLine())
//                .station(subway.getStation())
//                .longitude(subway.getLongitude())
//                .latitude(subway.getLatitude())
//                .build();
//    }
}
