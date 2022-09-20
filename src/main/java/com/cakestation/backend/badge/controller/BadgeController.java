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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/badge")
    public ProvideBage provideBadge(){
        
        ProvideBage result = badgeService.providebadge();

        return result;
    }

}
