package com.cakestation.backend.controller;

import com.cakestation.backend.domain.Subway;
import com.cakestation.backend.controller.dto.ResponseDto;
import com.cakestation.backend.service.SubwayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubwayController {

    private final SubwayService subwayService;

    // 지하철 역 전체 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/subway/all")
    public ResponseEntity getAllSubwayStation(){
        List<Subway> subways = subwayService.findAll();
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK.value(), true,"지하철역 전체 조회 성공", subways));
    }
}
