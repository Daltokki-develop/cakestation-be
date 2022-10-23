package com.cakestation.backend.cakestore.controller;

import com.cakestation.backend.cakestore.controller.dto.response.LikeStoreResponseInterface;
import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.cakestore.controller.dto.response.CakeStoreResponse;
import com.cakestation.backend.cakestore.service.CakeStoreService;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.config.JwtProperties;
import com.cakestation.backend.user.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
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
    @GetMapping("/stores/search")
    ResponseEntity<ApiResponse<List<CakeStoreResponse>>> searchStoresByKeyword(@RequestParam String keyword, @PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CakeStoreResponse> storeResponseList = cakeStoreService.searchStoresByKeyword(keyword, pageable)
                .stream().map(CakeStoreResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "가게들 조회 성공", storeResponseList));
    }


    // 가게 좋아요 하기 기능
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/stores/like/{storeId}")
    public ResponseEntity<ApiResponse<List<LikeStoreResponseInterface>>> likeStore(HttpServletRequest request, @PathVariable Long storeId){

        // 유저 정보 조회
        Optional<String> userEmail = Optional.ofNullable(utilService.getCurrentUserEmail(utilService.headerAccessToken(request, JwtProperties.HEADER_STRING).get()));

        // 헤더의 토큰 유효성
        userEmail.orElseThrow( () -> new IdNotFoundException("유저정보를 확인해주세요"));

        // 유저정보와 storeId를 통한 좋아요한 가게 저장
        List<LikeStoreResponseInterface> likedStoreResult = cakeStoreService.likeStore( storeId , userEmail.get());

        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "좋아요 성공", likedStoreResult));
    }

    @GetMapping("/stores/like/all")
    public ResponseEntity<ApiResponse<List<LikeStoreResponseInterface>>> likeStoreList(HttpServletRequest request){
        // 유저 정보 조회
        Optional<String> userEmail = Optional.ofNullable(utilService.getCurrentUserEmail(utilService.headerAccessToken(request, JwtProperties.HEADER_STRING).get()));

        // 헤더의 토큰 유효성
        userEmail.orElseThrow( () -> new IdNotFoundException("유저정보를 확인해주세요"));

        // 유저가 좋아요한 가게 List
        List<LikeStoreResponseInterface> resultList= cakeStoreService.findAllLikedStore(userEmail.get());

        return ResponseEntity.ok().body(new ApiResponse<>(HttpStatus.OK.value(), "테스트 성공" , resultList));
    }
}
