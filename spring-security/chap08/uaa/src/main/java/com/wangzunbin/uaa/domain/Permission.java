package com.wangzunbin.uaa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * ClassName:Permission
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/14 21:54
 */

@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wzb_permissions")
public class Permission implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "permission_name", unique = true, nullable = false, length = 50)
    private String authority;

    @NotNull
    @Size(max = 50)
    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) {
            return false;
        }
        return id != null && id.equals(((Permission) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authority);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "authority='" + authority + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
