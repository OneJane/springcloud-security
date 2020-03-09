package com.onejane.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 流控过滤器 限流
 */
@Component
@Order(1)
public class RateLimitFilter extends OncePerRequestFilter {

    //每秒的请求数为1，1秒一个请求。
    private RateLimiter rateLimiter = RateLimiter.create(1);

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(rateLimiter.tryAcquire()){
            filterChain.doFilter(request,response);
        }else{
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("too many requests!!!");
            response.getWriter().flush();
        }
    }
}
