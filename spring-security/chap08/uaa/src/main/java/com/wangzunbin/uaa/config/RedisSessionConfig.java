package com.wangzunbin.uaa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * ClassName:RedisSessionConfig
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 21:41
 */

@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig extends AbstractHttpSessionApplicationInitializer {
}
