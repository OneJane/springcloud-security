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
    @Order(3) // 审计日志应该在认证处理之后执行 Controller方法处理之前,链式Intercepter情况下，Intercepter按照声明的顺序一个接一个执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(3);
        AuditLog log = new AuditLog();
        log.setMethod(request.getMethod());
        log.setPath(request.getRequestURI());

//        UserInfo user = (UserInfo)request.getAttribute("user");
//        if(user!=null){
//            log.setUsername(user.getUsername());
//        }
        //save时发现AuditLog有@CreatedBy调用com.onejane.config.SecurityConfig.auditorAware的getCurrentAuditor方法拿到username后设置到userName上
        auditLogRepository.save(log);
        //下面要用 所以这里加一个attribute的属性
        request.setAttribute("auditLogId",log.getId());
        return true;
    }

    //postHandle  preHandle返回true,Controller方法处理完之后，DispatcherServlet进行视图的渲染之前，也就是说在这个方法中你可以对ModelAndView进行操作



    @Override // preHandle返回true,DispatcherServlet进行视图的渲染之后
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long auditLogId = (Long) request.getAttribute("auditLogId");

        AuditLog log = auditLogRepository.findById(auditLogId).get();
        log.setStatus(response.getStatus());
        auditLogRepository.save(log);

    }
}
