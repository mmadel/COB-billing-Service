package com.cob.billing.controller.admin;

import com.cob.billing.model.security.UserRoleScope;
import com.cob.billing.usecases.security.CreateUserRoleScopeUseCase;
import com.cob.billing.usecases.security.FindUserRolesScopeUseCase;
import com.cob.billing.usecases.security.UpdateUserRoleScopeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user/scope")
public class UserRoleScopeController {
    @Autowired
    CreateUserRoleScopeUseCase createUserRoleScopeUseCase;
    @Autowired
    UpdateUserRoleScopeUseCase updateUserRoleScopeUseCase;
    @Autowired
    FindUserRolesScopeUseCase findUserRolesScopeUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserRoleScope model) {
        createUserRoleScopeUseCase.create(model);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody UserRoleScope model) {
        updateUserRoleScopeUseCase.update(model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/uuid/{uuid}/roles/{roles}")
    public ResponseEntity find(@PathVariable String uuid, @PathVariable String[] roles) {
        return new ResponseEntity<>(findUserRolesScopeUseCase.find(uuid, roles), HttpStatus.OK);
    }
}
