package com.imooc.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpEntity;
//import com.dsw.security.order.OrderController;

//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * @author Liker
 *
 */
@Component
public class OAuthFilter extends ZuulFilter {
	
	private static Logger log = LoggerFactory.getLogger(OAuthFilter.class);
	
	private RestTemplate restTemplate = new RestTemplate();

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
		
		log.info("oauth start");
		
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		
		if(StringUtils.startsWith(request.getRequestURI(), "/token")) {
			return null;
		}
		
		String authHeader = request.getHeader("Authorization");
		
		if(StringUtils.isBlank(authHeader)) {
			return null;
		}
		
		if(!StringUtils.startsWithIgnoreCase(authHeader, "bearer ")) {
			return null;
		}
		
		try {
			// token info is the feedback of auth server
			TokenInfo info = getTokenInfo(authHeader);
			request.setAttribute("tokenInfo", info);
			
		} catch (Exception e) {
			log.error("get token info fail", e);
		}
		
		return null;
	}

	private TokenInfo getTokenInfo(String authHeader) {
		
		String token = StringUtils.substringAfter(authHeader, "bearer ");
		String oauthServiceUrl = "http://localhost:9090/oauth/check_token";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth("gateway", "123456");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("token", token);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
		
		log.info("token info :" + response.getBody().toString());
		
		return response.getBody();
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
		return 1;
	}

}