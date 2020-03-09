package com.onejane.config;

import com.onejane.filter.AclInterceptor;
import com.onejane.filter.AuditLogInterceptor;
import com.onejane.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLogInterceptor auditLogInterceptor;

    @Autowired
    private AclInterceptor aclInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInterceptor);
        registry.addInterceptor(aclInterceptor);
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                User user = (User) servletRequestAttributes.getRequest().getSession().getAttribute("user");
                String username = "";
                if(user!=null){
                    username = user.getUsername();
                }
                return Optional.of(username);
            }
        };

    }

}
