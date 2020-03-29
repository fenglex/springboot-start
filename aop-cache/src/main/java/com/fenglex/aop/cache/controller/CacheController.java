package com.fenglex.aop.cache.controller;

import com.fenglex.aop.cache.model.User;
import com.fenglex.aop.cache.service.CacheService;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {


    private final CacheService cacheService;


    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @RequestMapping("/cache")
    public User getUserInfo(User userP) {
        return cacheService.getUserInfo(userP);
    }
}
