package com.cakestation.backend.controller;

import com.cakestation.backend.controller.dto.ResponseDto;
import com.cakestation.backend.domain.Subway;
import com.cakestation.backend.service.StoreService;
import com.cakestation.backend.service.dto.request.CreateStoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/stores")
    public ResponseEntity uploadStore(@RequestBody @Validated CreateStoreDto createStoreDto){
        Long storeId = storeService.saveStore(createStoreDto);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED.value(),true,"가게 등록 성공", storeId));
    }

}
