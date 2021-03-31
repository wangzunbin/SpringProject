package com.wangzunbin.uaa.domain;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

/**
 * ClassName:Role
 * Function:
 * 角色实体类，实现 GrantedAuthority 接口
 * @author WangZunBin
 * @version 1.0 2021/3/31 21:44
 */

@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Entity
@Data
@Table(name = "wzb_roles")
public class Role implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增长 ID，唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称，有唯一约束，不能重复
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "role_name", unique = true, nullable = false, length = 50)
    private String authority;

}
