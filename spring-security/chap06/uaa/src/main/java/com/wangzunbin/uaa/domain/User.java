package com.wangzunbin.uaa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import com.wangzunbin.uaa.util.Constants;
import com.wangzunbin.uaa.validator.ValidEmail;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

/**
 * ClassName:User  <br/>
 * Function:  <br/>
 * 用户实体类，实现了 UserDetails 接口
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/28 23:27   <br/>
 */

/**
 * 用户实体类，实现了 UserDetails 接口
 */
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@QueryEntity
@Entity
@Table(name = "wzb_users")
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增长 ID，唯一标识
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String group;
    /**
     * 用户名
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String username;

    /**
     * 手机号
     */
    @Getter
    @Setter
    @NotNull
    @Pattern(regexp = Constants.PATTERN_MOBILE)
    @Size(min = 11, max = 11)
    @Column(length = 11, unique = true, nullable = false)
    private String mobile;

    /**
     * 姓名
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    /**
     * 是否激活，默认激活
     */
    @Getter
    @Setter
    @Builder.Default
    @NotNull
    @Column(nullable = false)
    private boolean enabled = true;

    /**
     * 账户是否未过期，默认未过期
     */
    @Builder.Default
    @NotNull
    @Getter
    @Setter
    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired = true;

    /**
     * 账户是否未锁定，默认未锁定
     */
    @Builder.Default
    @NotNull
    @Getter
    @Setter
    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked = true;

    /**
     * 密码是否未过期，默认未过期
     */
    @Builder.Default
    @NotNull
    @Getter
    @Setter
    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired = true;

    /**
     * 密码哈希
     */
    @JsonIgnore
    @NotNull
    @Getter
    @Setter
    @Size(min = 40, max = 80)
    @Column(name = "password_hash", length = 80, nullable = false)
    private String password;

    /**
     * 电邮地址
     */
    @ValidEmail
    @Getter
    @Setter
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true, nullable = false)
    private String email;

    /**
     * 是否启用两步验证
     */
    @Builder.Default
    @NotNull
    @Getter
    @Setter
    @Column(name = "using_mfa", nullable = false)
    private boolean usingMfa = false;

    /**
     * 两步验证的方式
     */
    @JsonIgnore
    @NotNull
    @Getter
    @Setter
    @Column(name = "mfa_key", nullable = false)
    private String mfaKey;

    /**
     * 角色列表，使用 Set 确保不重复
     */
    @Getter
    @Setter
    @Builder.Default
    @JsonIgnore
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "wzb_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> Stream.concat(
                        Stream.of(new SimpleGrantedAuthority(role.getRoleName())),
                        role.getPermissions().stream())
                )
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", mfaKey='" + mfaKey + '\'' +
                "}";
    }
}
