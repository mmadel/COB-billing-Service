package com.cob.billing.model.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserAccount {
    private Long  id ;
    private String uuid;
    private String name;
    private String email;
    private String userAccount;
    private String password;
    private List<RoleScope> roleScope;
}
