package com.wangzunbin.uaa.config;

import com.wangzunbin.uaa.aspect.RoleHierarchyReloadAspect;
import com.wangzunbin.uaa.service.rolehierarchy.RoleHierarchyService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import lombok.RequiredArgsConstructor;

/**
 * ClassName:AopConfig
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 21:20
 */

@RequiredArgsConstructor
@EnableAspectJAutoProxy
@Configuration
public class AopConfig {

    private final RoleHierarchyImpl roleHierarchy;
    private final RoleHierarchyService roleHierarchyService;

    @Bean
    public RoleHierarchyReloadAspect roleHierarchyReloadAspect() {
        return new RoleHierarchyReloadAspect(roleHierarchy, roleHierarchyService);
    }
}
