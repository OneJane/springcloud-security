package com.imooc.security.order;

import com.imooc.security.server.resource.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private RestTemplate restTemplate = new RestTemplate();

//    @Autowired
//    private OAuth2RestTemplate oAuth2RestTemplate;

    /**
     * http://localhost:8081/orders
     * {
     * 	"productId":1
     * }
     * Bearer Token:eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIl0sInVzZXJfbmFtZSI6Inl4MSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1ODY2NTk2ODEsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiM2IyNTU3ZjUtZmYwMC00NWJlLThlY2ItMzU0MzA5MzVjMjE0IiwiY2xpZW50X2lkIjoib3JkZXJBcHAifQ.f_cyMcWSCgKkhuf-fEMH2m738zvtd0QGQedEogfnj4NeizARZv7QTtuDdzw2i6HAyiFJ9x2nVfviat95LN5tWg8sMc-1pylDl9F2rKHGKM6P7Jx3hT14d7EU2hqq-vOBP8EL-E-TXfd-HhI04nYfqYNpqpxbXpFgcRN5cLYrnlFKzwdew8dboDeDr45NDgqhOZmZ1rdRwfwnQpQqSZ176eONbw6BJyJu_TbzLdj9qkEulRY3pwTiC1qN-I-LNjNBnjx0uti0k6SXB_ji3gsA2bsa7Oju6bQTvjdm6zR9IWt8IHrWIvbbMbWIvmZyUTF3mYQjvJFnfU1XWFrNlEqRfQ
     * 当请求中oath2的scope为read时，OAuth2ResourceServerConfig#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity) 返回403，read 不可访问POST
     *
     * {
     *     "id": null,
     *     "productId": 1
     * }
     *
     * http://localhost:8081/orders
     * Bearer Token:eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIl0sInVzZXJfbmFtZSI6Inl4MSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1ODY2NjEzODcsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiODYxNzM1MjEtODI5Yy00YmNkLWJhZGUtZDM5YWYxMTM1NTIzIiwiY2xpZW50X2lkIjoib3JkZXJBcHAifQ.Nlqzl4zQQpzpoQh6ev0ROg1857YlQ4rwnsc9XaffyOZ4iR0xA29NBztjwWIk5A8Qz48Cy_qhNd4PoucIbZv0gFD66KUNIBnD4W3IkAHWOzsm_qtozsseArA_4HJkGe0YW1mfJswd6BExyPzU79vrmSpx6OtjYn6GrpXFoj7qoZvFOHg0u05PJkaJeCdoxqHliE9sKVKzCQUhkpYB5S8yZSfRWvL-n92ZEaqy6PBtHPhrnM9uB5Kry3zXeYye9l9ULl5f16FBP7bXpKLyFfJaXUYwzRbIguzdAUKVIawxp45v-knyYgbDKtcTPVIGlXDwulp-bmR5VUmXlrsa7CL2FA
     * 控制台将打印yx1,来自OAuth2的获取token时的username
     *
     * 以上jwt形式获取token，OAuth2 默认使用Header,
     * Authorization:bearer 460ed432-bf8e-43d0-9cab-b1bb174225ba
     * HTTP Basic 默认使用Header
     * Authorization:basic 460ed432-bf8e-43d0-9cab-b1bb174225ba
     * @param info
     * @param user
     * @return
     */
    //创建订单
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public OrderInfo create(@RequestBody OrderInfo info,@AuthenticationPrincipal User user) {
        log.info("username  is {}",user.getUsername());
        log.info("price  is {}",info.getProductId());
//        PriceInfo price = oAuth2RestTemplate.getForObject("http://10.33.72.18:8082/prices/"+info.getProductId(),PriceInfo.class);
//        log.info("price  is {}",price.getPrice());
        return info;
    }

    /**
     * Get http://localhost:8081/orders/1
     * Bearer Token:eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIl0sInVzZXJfbmFtZSI6Inl4MSIsInNjb3BlIjpbInJlYWQiXSwiZXhwIjoxNTg2NjYwMzYxLCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIl0sImp0aSI6ImEwZGNlY2Q2LTNjMDUtNDUxMy05Mzg5LTg2NTNiOTBmNzQxZCIsImNsaWVudF9pZCI6Im9yZGVyQXBwIn0.jSQAQDoyuXji6J1Mzt7Jxw0aRhhJG4HSxYgt7M_WPyU6SQgHwrg3g_JoMjwTobMTKjAtxu5nYFPaZXo_56QK_s4EQj3aZP9QFFzE8ohIP-X-zHr4cSZvrmj3Dwq0V-i6BuSfORNBfDdijoP7fCJ288C0xpHc5jz6kdXH2nacmnjONdhXu6VJDQw-rUcui69pqx0Kvf9vrqvg1pms2-XxR0ALw2vy4CuzEWozgn1MxkppmwrUJGMhPa7jMbTx4e9Qm3mXk1LT1d0Nag7VBCaHSR9AzJaemuKGeeRmhfft578W-PBH2uM9YLgiHv4ywuEGP0Wcfc5UvhlNX55KewI_ZQ
     * 以上是由OAuth2的读scope生成的token
     *
     * Get http://localhost:8081/orders/1
     * Bearer Token:eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIl0sInVzZXJfbmFtZSI6Inl4MSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1ODY2NjEzODcsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiODYxNzM1MjEtODI5Yy00YmNkLWJhZGUtZDM5YWYxMTM1NTIzIiwiY2xpZW50X2lkIjoib3JkZXJBcHAifQ.Nlqzl4zQQpzpoQh6ev0ROg1857YlQ4rwnsc9XaffyOZ4iR0xA29NBztjwWIk5A8Qz48Cy_qhNd4PoucIbZv0gFD66KUNIBnD4W3IkAHWOzsm_qtozsseArA_4HJkGe0YW1mfJswd6BExyPzU79vrmSpx6OtjYn6GrpXFoj7qoZvFOHg0u05PJkaJeCdoxqHliE9sKVKzCQUhkpYB5S8yZSfRWvL-n92ZEaqy6PBtHPhrnM9uB5Kry3zXeYye9l9ULl5f16FBP7bXpKLyFfJaXUYwzRbIguzdAUKVIawxp45v-knyYgbDKtcTPVIGlXDwulp-bmR5VUmXlrsa7CL2FA
     * 只想要OAuth2中的username用户名
     * @param id
     * @return
     */
    //获取信息
    @GetMapping("/{id}")
    public OrderInfo getInfo(@PathVariable Long id,@AuthenticationPrincipal(expression = "#this.username") String username) {
        OrderInfo info = new OrderInfo();
        info.setId(id);
        info.setProductId(id*5);
        log.info("orderId  is {}",id);
        log.info("username  is {}",username);
//        PriceInfo price = restTemplate.getForObject("http://10.33.72.18:8082/prices/"+info.getProductId(),PriceInfo.class);
//        log.info("price  is {}",price.getPrice());
        return info;
    }


}
