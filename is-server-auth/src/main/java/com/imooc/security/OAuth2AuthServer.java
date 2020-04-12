package com.imooc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * POST http://localhost:8090/oauth/token
 * Authorization Basic username:orderApp password:123456
 * username:yx1
 * password:123456
 * grant_type:password
 * scope:read write
 */
@SpringBootApplication
public class OAuth2AuthServer {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2AuthServer.class,args);
    }
}
