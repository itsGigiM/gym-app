package com.example.taskspring.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private String token;

    @Override
    public void apply(RequestTemplate template) {
            template.header("Authorization", token);
    }
}