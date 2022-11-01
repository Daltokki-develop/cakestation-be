package com.cakestation.backend.store.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.dto.response.LikestoreResponseDto;
import com.cakestation.backend.store.dto.response.LikestoreResponseInterface;
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
    // 가게 좋아요 하기 기능
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/stores/like/{storeId}")
    public ResponseEntity<ApiResponse<List<LikestoreResponseInterface>>> likeStore(HttpServletRequest request, @PathVariable Long storeId){

        // 유저 정보 조회
        Optional<String> userEmail = Optional.ofNullable(utilService.getCurrentUserEmail(utilService.headerAccessToken(request, JwtProperties.HEADER_STRING).get()));

        // 헤더의 토큰 유효성
        userEmail.orElseThrow( () -> new IdNotFoundException("유저정보를 확인해주세요"));

        // 유저정보와 storeId를 통한 좋아요한 가게 저장
        List<LikestoreResponseInterface> likedStoreResult = storeService.likeStore( storeId , userEmail.get());

        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "좋아요 성공", likedStoreResult));
    }

    @GetMapping("/stores/like/all")
//    public ResponseEntity<ApiResponse<List<LikestoreResponseDto>>> likeStoreList(HttpServletRequest request){
        public ResponseEntity<ApiResponse<List<LikestoreResponseInterface>>> likeStoreList(HttpServletRequest request){
        // 유저 정보 조회
        Optional<String> userEmail = Optional.ofNullable(utilService.getCurrentUserEmail(utilService.headerAccessToken(request, JwtProperties.HEADER_STRING).get()));

        // 헤더의 토큰 유효성
        userEmail.orElseThrow( () -> new IdNotFoundException("유저정보를 확인해주세요"));

        // 유저가 좋아요한 가게 List
        List<LikestoreResponseInterface> resultList= storeService.findAllLikedStore(userEmail.get());

        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "테스트 성공" , resultList));
    }
}
