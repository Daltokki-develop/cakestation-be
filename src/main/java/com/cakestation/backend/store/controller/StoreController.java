package com.cakestation.backend.store.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.dto.response.StoreResponse;
import com.cakestation.backend.store.service.StoreService;
import com.cakestation.backend.store.dto.request.CreateStoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ApiResponse<Long>> uploadStore(@RequestBody @Validated CreateStoreDto createStoreDto) {
        Long storeId = storeService.saveStore(createStoreDto);
        return ResponseEntity.ok().body(
                new ApiResponse<Long>(HttpStatus.CREATED.value(), "가게 등록 성공", storeId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<StoreResponse>> showStore(@PathVariable Long storeId) {
        Store store = storeService.findStoreById(storeId);
        StoreResponse response = StoreResponse.from(store);
        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "가게 조회 성공", response));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/all")
    public ResponseEntity<ApiResponse<Page<StoreResponse>>> showAllStores(@PageableDefault(size = 10, sort = "uploadDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Store> storeList = storeService.findAllStores(pageable);
        Page<StoreResponse> storeResponseList = storeList.map(StoreResponse::from);
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", storeResponseList));
    }
}
