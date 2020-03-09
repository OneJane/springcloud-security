package com.onejane.filter;

import com.onejane.log.AuditLog;
import com.onejane.log.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 审计日志
 */
@Component
public class AuditLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    @Order(3)
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuditLog log = new AuditLog();
        log.setMethod(request.getMethod());
        log.setPath(request.getRequestURI());

//        UserInfo user = (UserInfo)request.getAttribute("user");
//        if(user!=null){
//            log.setUsername(user.getUsername());
//        }
        auditLogRepository.save(log);
        //下面要用 所以这里加一个attribute的属性
        request.setAttribute("auditLogId",log.getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long auditLogId = (Long) request.getAttribute("auditLogId");

        AuditLog log = auditLogRepository.findById(auditLogId).get();
        log.setStatus(response.getStatus());
        auditLogRepository.save(log);

    }
}
