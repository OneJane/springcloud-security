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
 *
 * {
 *     "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIl0sInVzZXJfbmFtZSI6Inl4MSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1ODY2NTk2ODEsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiM2IyNTU3ZjUtZmYwMC00NWJlLThlY2ItMzU0MzA5MzVjMjE0IiwiY2xpZW50X2lkIjoib3JkZXJBcHAifQ.f_cyMcWSCgKkhuf-fEMH2m738zvtd0QGQedEogfnj4NeizARZv7QTtuDdzw2i6HAyiFJ9x2nVfviat95LN5tWg8sMc-1pylDl9F2rKHGKM6P7Jx3hT14d7EU2hqq-vOBP8EL-E-TXfd-HhI04nYfqYNpqpxbXpFgcRN5cLYrnlFKzwdew8dboDeDr45NDgqhOZmZ1rdRwfwnQpQqSZ176eONbw6BJyJu_TbzLdj9qkEulRY3pwTiC1qN-I-LNjNBnjx0uti0k6SXB_ji3gsA2bsa7Oju6bQTvjdm6zR9IWt8IHrWIvbbMbWIvmZyUTF3mYQjvJFnfU1XWFrNlEqRfQ",
 *     "token_type": "bearer",
 *     "expires_in": 3599,
 *     "scope": "read write",
 *     "jti": "3b2557f5-ff00-45be-8ecb-35430935c214"
 * }
 *
 * 数据库oauth_client_details
 * orderApp	order-server	$2a$10$delk3h0IiHmP3LYrB8qRge.KMGu7Tj8MM8B0RRcF3Qz9QBwQRHZvy	read,write	password			3600
 * orderService	order-server	$2a$10$z1Di6LqPkcNJTjA0Ks5.ne6q2dE.jSIelJQB4eXsjSiN41uBFtXLK	read	password			3600	30	{"systemInfo":"Atlas System"}	true
 */
@SpringBootApplication
public class OAuth2AuthServer {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2AuthServer.class,args);
    }
}
