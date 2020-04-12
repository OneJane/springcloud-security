package com.imooc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * POST 10.33.72.18:9070/token/oauth/token
 * Basic Auth:
 * 	orderApp:123456
 * Body:
 *  username:yx1
 *  grant_type:password
 *  scope:read write
 *  password:123456
 *
 * {
 *     "access_token": "150e4006-30fb-44ab-b3f2-72dd60a10939",
 *     "token_type": "bearer",
 *     "expires_in": 2408,
 *     "scope": "read write"
 * }
 *
 * http://10.33.72.18:9070/order/orders
 * Body:
 *  {
 *  	"productId":1
 *  }
 * Headers:
 *  Content-Type: application/json
 *  Authorization: bearer 150e4006-30fb-44ab-b3f2-72dd60a10939
 *
 *  {
 *     "id": null,
 *     "productId": 1
 * }
 */
//@Configuration
@SpringBootApplication
@EnableZuulProxy
public class GatewayServer {
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayServer.class, args);
	}

}