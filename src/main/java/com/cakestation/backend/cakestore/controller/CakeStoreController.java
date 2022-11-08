package com.cakestation.backend.cakestore.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.cakestore.controller.dto.response.CakeStoreResponse;
import com.cakestation.backend.cakestore.service.CakeStoreService;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
import com.cakestation.backend.config.JwtProperties;
import com.cakestation.backend.user.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CakeStoreController {

    private final CakeStoreService cakeStoreService;
    private final UtilService utilService;

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/search/name")
    ResponseEntity<ApiResponse<List<CakeStoreResponse>>> searchStoresByKeyword(@RequestParam String storeName) {
        List<CakeStoreResponse> storeResponseList = cakeStoreService.searchStoresByKeyword(storeName)
                .stream().map(CakeStoreResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", storeResponseList));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/search/station")
    ResponseEntity<ApiResponse<List<CakeStoreResponse>>> searchStoresByStation(@RequestParam String stationName) {
        List<CakeStoreResponse> storeResponseList = cakeStoreService.searchStoresByStation(stationName)
                .stream().map(CakeStoreResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", storeResponseList));
    }


    // 가게 좋아요 하기 기능
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/stores/like/{storeId}")
    public ResponseEntity<ApiResponse<Long>> likeStore(HttpServletRequest request, @PathVariable Long storeId) {

        // 유저 정보 조회
        String userEmail = utilService.getCurrentUserEmail(request.getHeader(JwtProperties.HEADER_STRING));

        // 헤더의 토큰 유효성
//        userEmail.orElseThrow(() -> new IdNotFoundException("유저정보를 확인해주세요"));

        // 유저정보와 storeId를 통한 좋아요한 가게 저장
        Long likeStoreId = cakeStoreService.likeStore(storeId, userEmail);

        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "좋아요 성공", likeStoreId));
    }

    @GetMapping("/stores/like/all")
    public ResponseEntity<ApiResponse<List<CakeStoreDto>>> likeStoreList(HttpServletRequest request) {
        // 유저 정보 조회
        String userEmail = utilService.getCurrentUserEmail(request.getHeader(JwtProperties.HEADER_STRING));

        // 헤더의 토큰 유효성
//        userEmail.orElseThrow(() -> new IdNotFoundException("유저정보를 확인해주세요"));

        // 유저가 좋아요한 가게 List
        List<CakeStoreDto> cakeStoreDtoList = cakeStoreService.findAllLikeStore(userEmail);

        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), "좋아요 리스트 조회 성공", cakeStoreDtoList));
    }
}
