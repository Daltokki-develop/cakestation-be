package com.cakestation.backend.cakestore.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.cakestore.controller.dto.response.CakeStoreResponse;
import com.cakestation.backend.cakestore.service.CakeStoreService;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
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
        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "가게 등록 성공", storeId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<CakeStoreResponse>> showStore(@PathVariable Long storeId) {
        CakeStoreResponse storeResponse = CakeStoreResponse.from(cakeStoreService.findStoreById(storeId));
        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), "가게 조회 성공", storeResponse));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/all")
    public ResponseEntity<ApiResponse<List<CakeStoreResponse>>> showAllStores() {
        List<CakeStoreResponse> storeResponseList = cakeStoreService.findAllStore()
                .stream()
                .map(CakeStoreResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "가게 리스트 조회 성공", storeResponseList));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/search/store")
    ResponseEntity<ApiResponse<List<CakeStoreResponse>>> searchStoresByKeyword(
            @RequestParam String name,
            @PageableDefault(size = 30, sort = {"reviewCount", "reviewScore"}, direction = Sort.Direction.DESC) Pageable pageable) {

        List<CakeStoreResponse> storeResponseList = cakeStoreService.searchStoresByKeyword(name, pageable)
                .stream()
                .map(CakeStoreResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), "가게 리스트 조회 성공", storeResponseList));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/search/station")
    ResponseEntity<ApiResponse<List<CakeStoreResponse>>> searchStoresByStation(
            @RequestParam String name,
            @PageableDefault(size = 30, sort = {"reviewCount", "reviewScore"}, direction = Sort.Direction.DESC) Pageable pageable) {

        List<CakeStoreResponse> storeResponseList = cakeStoreService.searchStoresByStation(name, pageable)
                .stream()
                .map(CakeStoreResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), "가게 리스트 조회 성공", storeResponseList));
    }


    // 가게 좋아요 하기 기능
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/stores/{storeId}/like")
    public ResponseEntity<Void> likeStore(HttpServletRequest request, @PathVariable Long storeId) {

        String userEmail = utilService.getCurrentUserEmail(request.getHeader(JwtProperties.HEADER_STRING));
        cakeStoreService.likeStore(storeId, userEmail);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/like/all")
    public ResponseEntity<ApiResponse<List<CakeStoreDto>>> likeStoreList(HttpServletRequest request) {

        String userEmail = utilService.getCurrentUserEmail(request.getHeader(JwtProperties.HEADER_STRING));
        List<CakeStoreDto> cakeStoreDtoList = cakeStoreService.findAllLikeStore(userEmail);

        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), "좋아요 리스트 조회 성공", cakeStoreDtoList));
    }
}
