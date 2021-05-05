package com.wangzunbin.uaa.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import redis.embedded.RedisServer;

/**
 * ClassName:CacheConfig
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 21:41
 */
@Slf4j
@RequiredArgsConstructor
@EnableCaching
@Configuration
@ConditionalOnProperty(prefix = "spring.redis", value = "host")
public class CacheConfig {

    private final RedisProperties redisProperties;
    private final Environment env;
    private RedisServer redisServer;

    @PostConstruct
    void initialize() {
        // 仅在测试环境和开发环境启用嵌入式 redis
        if (!Arrays.asList(env.getActiveProfiles()).contains("prod")) {
            redisServer = new RedisServer(redisProperties.getPort());
            redisServer.start();
        }
    }

    @PreDestroy
    void deInit() {
        // 仅在测试环境和开发环境关闭嵌入式 redis
        if (!Arrays.asList(env.getActiveProfiles()).contains("prod")) {
            redisServer.stop();
        }
    }

    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        // sentinel
        if (redisProperties.getSentinel() != null) {
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
            val nodes = redisProperties.getSentinel().getNodes();
            sentinelServersConfig.addSentinelAddress(nodes.toArray(new String[0]));
            sentinelServersConfig.setDatabase(redisProperties.getDatabase());
            if (redisProperties.getPassword() != null) {
                sentinelServersConfig.setPassword(redisProperties.getPassword());
            }
        } else { // 单个 Server
            SingleServerConfig singleServerConfig = config.useSingleServer();
            // format as redis://127.0.0.1:7181 or rediss://127.0.0.1:7181 for SSL
            String schema = redisProperties.isSsl() ? "rediss://" : "redis://";
            singleServerConfig.setAddress(schema + redisProperties.getHost() + ":" + redisProperties.getPort());
            singleServerConfig.setDatabase(redisProperties.getDatabase());
            if (redisProperties.getPassword() != null) {
                singleServerConfig.setPassword(redisProperties.getPassword());
            }
        }
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        log.debug("生成缓存处理器");
        return new RedissonSpringCacheManager(redissonClient);
    }
}
