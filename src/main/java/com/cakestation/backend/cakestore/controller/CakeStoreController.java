package com.cakestation.backend.cakestore.controller;

import com.cakestation.backend.cakestore.service.CakeStoreQueryService;
import com.cakestation.backend.common.dto.ApiResponse;
import com.cakestation.backend.cakestore.controller.dto.response.CakeStoreResponse;
import com.cakestation.backend.cakestore.service.CakeStoreService;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.cakestation.backend.common.auth.AuthUtil.getCurrentUserEmail;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CakeStoreController {

    private final CakeStoreService cakeStoreService;
    private final CakeStoreQueryService cakeStoreQueryService;

    @PostMapping("/stores")
    public ResponseEntity<ApiResponse<Long>> uploadStore(@RequestBody @Validated CreateCakeStoreDto createStoreDto) {
        Long storeId = cakeStoreService.saveStore(createStoreDto);

        return ResponseEntity.created(URI.create("/api/stores/" + storeId)).build();
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ApiResponse<CakeStoreResponse>> showStore(@PathVariable Long storeId) {
        CakeStoreResponse storeResponse = CakeStoreResponse.from(cakeStoreQueryService.findStoreById(storeId));

        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), storeResponse));
    }

    @DeleteMapping("/stores/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId) {
        cakeStoreService.deleteStore(storeId);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/stores/all")
    public ResponseEntity<ApiResponse<List<CakeStoreResponse>>> showAllStores() {
        List<CakeStoreResponse> storeResponseList = cakeStoreQueryService.findAllStore()
                .stream()
                .map(CakeStoreResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), storeResponseList));
    }

    @GetMapping("/stores/search/store")
    ResponseEntity<ApiResponse<List<CakeStoreResponse>>> searchStoresByKeyword(
            @RequestParam String name,
            @PageableDefault(size = 30, sort = {"reviewCount", "reviewScore"}, direction = Sort.Direction.DESC) Pageable pageable) {

        List<CakeStoreResponse> storeResponseList = cakeStoreQueryService.searchStoresByName(name, pageable)
                .stream()
                .map(CakeStoreResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), storeResponseList));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/search/station")
    ResponseEntity<ApiResponse<List<CakeStoreResponse>>> searchStoresByStation(
            @RequestParam String name,
            @PageableDefault(size = 30, sort = {"reviewCount", "reviewScore"}, direction = Sort.Direction.DESC) Pageable pageable) {

        List<CakeStoreResponse> storeResponseList = cakeStoreQueryService.searchStoresByStation(name, pageable)
                .stream()
                .map(CakeStoreResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), storeResponseList));
    }

    @PostMapping("/stores/{storeId}/like")
    public ResponseEntity<Void> likeStore(@PathVariable Long storeId) {
        cakeStoreService.likeStore(storeId, getCurrentUserEmail());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/like/all")
    public ResponseEntity<ApiResponse<List<CakeStoreDto>>> likeStoreList() {
        List<CakeStoreDto> cakeStoreDtoList = cakeStoreQueryService.findAllLikeStore(getCurrentUserEmail());

        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), cakeStoreDtoList));
    }
}
