package com.wangzunbin.uaa.aspect;

import com.wangzunbin.uaa.service.rolehierarchy.RoleHierarchyService;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * ClassName:RoleHierarchyReloadAspect
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/19 21:15
 */

@Slf4j
@RequiredArgsConstructor
@Aspect
public class RoleHierarchyReloadAspect {

    private final RoleHierarchyImpl roleHierarchy;
    private final RoleHierarchyService roleHierarchyService;

    /**
     * 在表达式 <code>* com.wangzunbin.uaa.service.admin.*.*(..)</code> 中
     * 第一个 * 的位置表示方法的返回类型，* 指的是任意类型
     * 然后 com.imooc.uaa.service.admin.*.* 指定的是方法，这里要指定完整的方法名，*.* 表示 package 下任意方法
     * 最后 (..) 指定的是方法参数
     */
    @Pointcut("execution(* com.wangzunbin.uaa.service.admin.*.*(..))")
    public void applicationPackagePointcut() {
    }

    /**
     * 在方法成功执行后执行下面方法
     */
    @AfterReturning("applicationPackagePointcut() && @annotation(com.wangzunbin.uaa.annotation.ReloadRoleHierarchy)")
    public void reloadRoleHierarchy() {
        val roleMap = roleHierarchyService.getRoleHierarchyExpr();
        roleHierarchy.setHierarchy(roleMap);
        log.debug("RoleHierarchy Reloaded");
    }
}
