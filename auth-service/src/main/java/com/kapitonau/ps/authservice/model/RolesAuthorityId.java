package com.kapitonau.ps.authservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RolesAuthorityId implements Serializable {
    private static final long serialVersionUID = -3161280115945270380L;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "authority_id", nullable = false)
    private Long authorityId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RolesAuthorityId entity = (RolesAuthorityId) o;
        return Objects.equals(this.authorityId, entity.authorityId) &&
                Objects.equals(this.roleId, entity.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorityId, roleId);
    }

}