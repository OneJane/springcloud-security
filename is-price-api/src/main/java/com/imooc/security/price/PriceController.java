package com.imooc.security.price;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/prices")
@Slf4j
public class PriceController {


    //返回商品价格的信息
    @GetMapping("/{id}")
    public PriceInfo getPrice(@PathVariable Long id, @AuthenticationPrincipal String username) {
        log.info("username is {}",username);
        log.info("productId is {}",id);
        PriceInfo info  = new PriceInfo();
        info.setId(id);
        info.setPrice((new BigDecimal(100)));
        return info;
    }

}
