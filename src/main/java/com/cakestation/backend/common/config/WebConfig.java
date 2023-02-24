package com.cakestation.backend.common.config;

import com.cakestation.backend.common.auth.AuthorizationFilter;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final KakaoService kakaoService;
    private final UserRepository userRepository;

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> logFilter() {

        FilterRegistrationBean<AuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthorizationFilter(kakaoService, userRepository));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/api/*");

        return filterRegistrationBean;
    }
}
