package com.cakestation.backend.store.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.store.controller.dto.response.CakeStoreResponse;
import com.cakestation.backend.store.service.CakeStoreService;
import com.cakestation.backend.store.service.dto.CakeStoreDto;
import com.cakestation.backend.store.service.dto.CreateCakeStoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CakeStoreController {

    private final CakeStoreService cakeStoreService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/stores")
    public ResponseEntity<ApiResponse<Long>> uploadStore(@RequestBody @Validated CreateCakeStoreDto createStoreDto) {
        Long storeId = cakeStoreService.saveStore(createStoreDto);
        return ResponseEntity.ok().body(
                new ApiResponse<Long>(HttpStatus.CREATED.value(), "가게 등록 성공", storeId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<CakeStoreResponse>> showStore(@PathVariable Long storeId) {
        CakeStoreResponse storeResponse = CakeStoreResponse.from(cakeStoreService.findStoreById(storeId));
        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "가게 조회 성공", storeResponse));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/all")
    public ResponseEntity<ApiResponse<List<CakeStoreResponse>>> showAllStores() {
        List<CakeStoreDto> storeList = cakeStoreService.findAllStore();
        List<CakeStoreResponse> storeResponseList = storeList.stream().map(CakeStoreResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", storeResponseList));
    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/stores/search")
//    ResponseEntity<ApiResponse<List<StoreResponse>>> searchStoresByKeyword(@RequestParam String keyword, @PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
//        List<StoreResponse> storeResponseList = storeService.searchStoresByKeyword(keyword, pageable)
//                .stream().map(StoreResponse::from).collect(Collectors.toList());
//        return ResponseEntity.ok().body(
//                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", storeResponseList));
//    }

//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("/stores")
//    public ResponseEntity<ApiResponse<StoreResponse>> likeStore(@RequestParam String storeId){
//
//
//
//        return ResponseEntity.ok().body(
//                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", "좋아요한 가게"));
//    }
}
