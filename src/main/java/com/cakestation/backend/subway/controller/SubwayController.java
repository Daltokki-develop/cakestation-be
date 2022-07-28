package com.cakestation.backend.subway.controller;

import com.cakestation.backend.subway.domain.Subway;
import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.subway.dto.response.SubwayResponse;
import com.cakestation.backend.subway.service.SubwayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubwayController {

    private final SubwayService subwayService;

    // 지하철 역 전체 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/subway/all")
    public ResponseEntity<ApiResponse> getAllSubwayStation(){
        List<Subway> subways = subwayService.findAll();
        List<SubwayResponse> response = subways.stream().map(SubwayResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(new ApiResponse(HttpStatus.OK.value(), true,"지하철역 전체 조회 성공", response));
    }
}
