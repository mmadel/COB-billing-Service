package com.cob.billing.model.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserRoleScope {
    private Long  id ;
    private String uuid;
    private List<RoleScope> roleScope;
}
