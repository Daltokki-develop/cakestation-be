package com.cakestation.backend.config;

//import com.cakestation.backend.common.Filter.Filter;
import com.cakestation.backend.common.Filter.LoginFilter;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UtilService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    private final KakaoService kakaoService;
    private final UtilService utilService;
    private final KakaoConfig kakaoConfig;

    public WebConfig(KakaoService kakaoService, UtilService utilService, KakaoConfig kakaoConfig) {
        this.kakaoService = kakaoService;
        this.utilService = utilService;
        this.kakaoConfig = kakaoConfig;
    }

    @Bean
    public FilterRegistrationBean<LoginFilter> logFilter() {
    
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter(kakaoService, utilService, kakaoConfig));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
