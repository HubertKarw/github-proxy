package com.hubertkarw.github_proxy.config;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignClientConfig {

    @Bean
    Retryer retryer(){
        return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 3);
    }
    @Bean
    ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
