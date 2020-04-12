package com.imooc.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jojo
 *
 */
@Slf4j
@Component
public class AuthorizationFilter extends ZuulFilter {
	
	private static Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);

	/* (non-Javadoc)
	 * @see com.netflix.zuul.IZuulFilter#shouldFilter()
	 */
	public boolean shouldFilter() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.netflix.zuul.IZuulFilter#run()
	 */
	public Object run() throws ZuulException {
		
		log.info("authorization start");
		
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		
		if(isNeedAuth(request)) {
			
			TokenInfo tokenInfo = (TokenInfo)request.getAttribute("tokenInfo");
			
			if(tokenInfo != null && tokenInfo.isActive()) {
				if(!hasPermission(tokenInfo, request)) {
					log.info("audit log update fail 403");
					handleError(403, requestContext);
				}
				// all pass then call order server
				requestContext.addZuulRequestHeader("username", tokenInfo.getUser_name());
			}else {
				if(!StringUtils.startsWith(request.getRequestURI(), "/token")) {
					log.info("audit log update fail 401");
					handleError(401, requestContext);
				}
			}
		}
		
		return null;
	}
	
	private void handleError(int status, RequestContext requestContext) {
		requestContext.getResponse().setContentType("application/json");
		requestContext.setResponseStatusCode(status);
		requestContext.setResponseBody("{\"message\":\"auth fail\"}");
		// when get error then back, no more continue
		requestContext.setSendZuulResponse(false);
	}

	private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
		// need compare with db
//		return RandomUtils.nextInt() % 2 == 0;
		return true; 
	}

	private boolean isNeedAuth(HttpServletRequest request) {
		// need compare with db
		return true;
	}

	/* (non-Javadoc)
	 * @see com.netflix.zuul.ZuulFilter#filterType()
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/* (non-Javadoc)
	 * @see com.netflix.zuul.ZuulFilter#filterOrder()
	 */
	@Override
	public int filterOrder() {
		return 3;
	}

}