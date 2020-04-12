package com.imooc.security.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *  里面只有一个方法叫做loadUserByUserName。
     *  这个方法的作用就是通过用户名来获取详细的用户信息。
     *  一般情况下这个接口的实现类需要我们自己去写。在实现类里面通过去查询数据库拿着这个用户名。
     *  查出相关的用户信息来。
     *  如果查不到用户，就抛出UsernameNotFundException
     */
    @Autowired
    private UserDetailsService userDetailsService;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
    // encrypt password
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	// get user id and password from input
        auth.userDetailsService(userDetailsService)
        // encrypt password and compare password
            .passwordEncoder(passwordEncoder());
    }

    // validate userid and pw
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.anyRequest().authenticated()
			.and()
//			.formLogin().loginPage(loginPage).and()
		.formLogin().and()
		.httpBasic().and()
		.logout()
			.logoutSuccessHandler(logoutSuccessHandler);
	}
}
