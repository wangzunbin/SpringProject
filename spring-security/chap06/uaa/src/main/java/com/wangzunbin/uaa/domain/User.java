package com.wangzunbin.uaa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * ClassName:User  <br/>
 * Function:  <br/>
 * 用户实体类，实现了 UserDetails 接口
 *
 * @author WangZunBin <br/>
 * @version 1.0 2021/2/28 23:27   <br/>
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Data
@Entity
@Table(name = "wzb_users")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 50, message = "用户畅读必须在4到50个字符之间")
    @Column(length = 50, unique = true, nullable = false)
    private String username;
//    @ValidPassword
    @JsonIgnore
    @Column(name = "password_hash", length = 80, nullable = false)
    private String password;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(length = 11, unique = true, nullable = false)
    private String mobile;

    @Email
    @Column(length = 255, unique = true, nullable = false)
    private String email;

    @Column(length = 50)
    private String name;

    @Column(nullable = false)
    private boolean enabled;

    /**
     * 是否启用两步验证
     */
    @Builder.Default
    @Column(name = "using_mfa", nullable = false)
    private boolean usingMfa = false;

    /**
     * 两步验证的key
     */
    @JsonIgnore
    @Column(name = "mfa_key", nullable = false)
    private String mfaKey;

    // 账号是否过期
    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired;

    // 账号是否锁定
    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked;

    // 密码是否过期
    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired;

    /**
     * 角色列表，使用 Set 确保不重复
     */
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "wzb_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> Stream.concat(
                        Stream.of(new SimpleGrantedAuthority(role.getRoleName())),
                        role.getPermissions().stream())
                )
                .collect(Collectors.toList());
    }
}
