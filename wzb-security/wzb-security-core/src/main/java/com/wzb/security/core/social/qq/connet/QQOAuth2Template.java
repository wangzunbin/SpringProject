/**
 * 
 */
package com.wzb.security.core.social.qq.connet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * ClassName:QQOAuth2Template  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/7 22:29   <br/>
 */
public class QQOAuth2Template extends OAuth2Template {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		// 这两个需要设置为true, 它才回携带数据
		setUseParametersForClientAuthentication(true);
	}

	/**
	 * 这是调用QQ认证完之后, 调用此方法
	 * @param accessTokenUrl  去调用的接口
	 * @param parameters 入参
	 * @return  返回认证结果
	 */
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		
		logger.info("获取accessToke的响应："+responseStr);
		
		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
		
		String accessToken = StringUtils.substringAfterLast(items[0], "=");
		Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
		String refreshToken = StringUtils.substringAfterLast(items[2], "=");
		
		return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}
	
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate;
	}

}
