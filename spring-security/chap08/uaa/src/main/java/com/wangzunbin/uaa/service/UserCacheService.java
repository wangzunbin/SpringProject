package com.wangzunbin.uaa.service;

import com.wangzunbin.uaa.domain.User;
import com.wangzunbin.uaa.util.Constants;
import com.wangzunbin.uaa.util.CryptoUtil;
import com.wangzunbin.uaa.util.TotpUtil;

import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * ClassName:UserCacheService
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 22:00
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCacheService {

    private final RedissonClient redissonClient;
    private final CryptoUtil cryptoUtil;
    private final TotpUtil totpUtil;

    // 用mfaId缓存User
    public String cacheUser(User user){
        val mfaId = cryptoUtil.randomAlphanumeric(12);
        log.debug("生成 mfaId: {}", mfaId);
        RMapCache<String, User> cache = redissonClient.getMapCache(Constants.CACHE_MFA);
        if (!cache.containsKey(mfaId)) {
            cache.put(mfaId, user, totpUtil.getTimeStepInLong(), TimeUnit.SECONDS);
        }
        return mfaId;
    }

    // 根据mfaId取出User
    public Optional<User> retrieveUser(String mfaId) {
        log.debug("输入参数 mfaId: {}", mfaId);
        RMapCache<String, User> cache = redissonClient.getMapCache(Constants.CACHE_MFA);
        if (cache.containsKey(mfaId)) {
            log.debug("找到 mfaId {}", mfaId);
            return Optional.of(cache.get(mfaId));
        }
        return Optional.empty();
    }

    /**
     *
     * @param mfaId mfaId
     * @param code 验证码
     * @return 校验后的User对象
     */
    public Optional<User> verifyTotp(String mfaId, String code) {
        log.debug("输入参数 mfaId: {}, code: {}", mfaId, code);
        RMapCache<String, User> cache = redissonClient.getMapCache(Constants.CACHE_MFA);
        if (!cache.containsKey(mfaId) || cache.get(mfaId) == null) {
            return Optional.empty();
        }
        val cachedUser = cache.get(mfaId);
        log.debug("找到用户 {}", cachedUser);
        try {
            val isValid = totpUtil.validateTotp(totpUtil.decodeKeyFromString(cachedUser.getMfaKey()), code);
            log.debug("code {} 的验证结果为 {}", code, isValid);
            if (!isValid) {
                return Optional.empty();
            }
            cache.remove(mfaId);
            log.debug("移除 mfaId: {}", mfaId);
            return Optional.of(cachedUser);
        } catch (InvalidKeyException e) {
            log.error("Key is invalid {}", e.getLocalizedMessage());
        }
        return Optional.empty();
    }
}
