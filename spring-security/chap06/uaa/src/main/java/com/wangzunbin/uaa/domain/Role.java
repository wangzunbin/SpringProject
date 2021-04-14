package com.wangzunbin.uaa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangzunbin.uaa.util.Constants;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@Entity
@Data
@Table(name = "wzb_roles")
public class Role implements Serializable {

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
    /**
     * 角色名称，有唯一约束，不能重复
     */
    @NotNull
    @Pattern(regexp = Constants.PATTERN_ROLE_NAME)
    @Column(name = "role_name", unique = true, nullable = false, length = 50)
    private String roleName;

    @Builder.Default
    // 不要序列化
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    @ManyToMany
    @JoinTable(
            name = "wzb_roles_permissions",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Permission> permissions = new HashSet<>();

    // 不要序列化
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
