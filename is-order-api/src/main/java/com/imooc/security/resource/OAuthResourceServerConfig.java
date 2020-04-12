package com.imooc.security.resource;//package com.dsw.security.server.resource;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//
//@Configuration
//@EnableResourceServer
//public class OAuthResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("order-server");
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
////    	a除了haha请求，其他都需要验证令牌
////        http.authorizeRequests().antMatchers("/haha").permitAll()
////                .anyRequest().authenticated();
//    	//a实现ACL权限控制，申请只有读或者只有写权限的token
//      http.authorizeRequests()
//      	  .antMatchers(HttpMethod.POST).access("#oauth2.hasScope('write')")
//      	  .antMatchers(HttpMethod.GET).access("#oauth2.hasScope('read')");
//    	
//    }
//
//}
