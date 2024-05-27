package com.cob.billing.model.security;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class RoleScope {
    private String role;
    private String scope;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleScope roleScope = (RoleScope) o;
        return role.equals(roleScope.role) && scope.equals(roleScope.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, scope);
    }
}
