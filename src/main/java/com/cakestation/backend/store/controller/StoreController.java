package com.cakestation.backend.store.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.store.service.StoreService;
import com.cakestation.backend.store.dto.request.CreateStoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/stores")
    public ResponseEntity uploadStore(@RequestBody @Validated CreateStoreDto createStoreDto){
        Long storeId = storeService.saveStore(createStoreDto);
        return ResponseEntity.ok().body(new ApiResponse(HttpStatus.CREATED.value(),true,"가게 등록 성공", storeId));
    }

}
