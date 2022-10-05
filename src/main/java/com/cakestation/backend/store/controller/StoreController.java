package com.cakestation.backend.store.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.dto.response.StoreResponse;
import com.cakestation.backend.store.service.StoreDto;
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

import java.util.List;
import java.util.stream.Collectors;

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
        StoreResponse storeResponse = StoreResponse.from(storeService.findStoreById(storeId));
        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "가게 조회 성공", storeResponse));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/all")
    public ResponseEntity<ApiResponse<List<StoreResponse>>> showAllStores() {
        List<StoreDto> storeList = storeService.findAllStore();
        List<StoreResponse> storeResponseList = storeList.stream().map(StoreResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", storeResponseList));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/search")
    ResponseEntity<ApiResponse<List<StoreResponse>>> searchStoresByKeyword(@RequestParam String keyword, @PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        List<StoreResponse> storeResponseList = storeService.searchStoresByKeyword(keyword, pageable)
                .stream().map(StoreResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", storeResponseList));
    }

}
