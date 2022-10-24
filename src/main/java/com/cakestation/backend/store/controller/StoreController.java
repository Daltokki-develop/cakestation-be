package com.cakestation.backend.store.controller;

import com.cakestation.backend.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/stores")
//    public ResponseEntity<ApiResponse<Long>> uploadStore(@RequestBody @Validated CreateStoreDto createStoreDto) {
//        Long storeId = storeService.saveStore(createStoreDto);
//        return ResponseEntity.ok().body(
//                new ApiResponse<Long>(HttpStatus.CREATED.value(), "가게 등록 성공", storeId));
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/stores/{storeId}")
//    public ResponseEntity<ApiResponse<StoreResponse>> showStore(@PathVariable Long storeId) {
//        StoreResponse storeResponse = StoreResponse.from(storeService.findStoreById(storeId));
//        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "가게 조회 성공", storeResponse));
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/stores/all")
//    public ResponseEntity<ApiResponse<List<StoreResponse>>> showAllStores() {
//        List<StoreDto> storeList = storeService.findAllStore();
//        List<StoreResponse> storeResponseList = storeList.stream().map(StoreResponse::from).collect(Collectors.toList());
//        return ResponseEntity.ok().body(
//                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", storeResponseList));
//    }
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
