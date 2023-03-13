package com.cakestation.backend.badge.controller;

import lombok.RequiredArgsConstructor;

import com.cakestation.backend.badge.service.dto.response.ProvideBage;
import com.cakestation.backend.badge.service.BadgeService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BadgeController {
    
    private final BadgeService badgeService;

    @PostMapping("/badge")
    public ProvideBage provideBadge(){
        // TODO(버전 업데이트 시 추후 개발 예정)
        ProvideBage result = badgeService.providebadge();

        return result;
    }
}
