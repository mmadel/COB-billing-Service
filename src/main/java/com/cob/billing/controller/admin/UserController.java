package com.cob.billing.controller.admin;

import com.cob.billing.exception.business.UserException;
import com.cob.billing.model.security.UserAccount;
import com.cob.billing.usecases.security.*;
import com.cob.billing.usecases.user.FindUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/user")
@PreAuthorize("hasAnyRole('admin-tool-role','account-management-admin-tool-role')")
public class UserController {

    @Autowired
    UpdateUserRoleScopeUseCase updateUserRoleScopeUseCase;
    @Autowired
    FindUserRolesScopeUseCase findUserRolesScopeUseCase;
    @Autowired
    FindUserUseCase findUserUseCase;
    @Autowired
    CreateUserUseCase createUserUseCase;
    @Autowired
    FindUserAccountUseCase findUserAccountUseCase;
    @Autowired
    DeleteUserUseCase deleteUserUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserAccount model) throws UserException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        createUserUseCase.create(model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/uuid/{uuid}")
    public ResponseEntity delete(@PathVariable String uuid) throws UserException {
        deleteUserUseCase.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/scope/update")
    public ResponseEntity update(@RequestBody UserAccount model) {
        updateUserRoleScopeUseCase.update(model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/scope/find/uuid/{uuid}/roles/{roles}")
    public ResponseEntity findScope(@PathVariable String uuid, @PathVariable String[] roles) {
        return new ResponseEntity<>(findUserRolesScopeUseCase.find(uuid, roles), HttpStatus.OK);
    }

    @GetMapping("/find/users")
    public ResponseEntity findUsers() {
        return new ResponseEntity(findUserUseCase.find(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/find/uuid/{uuid}")
    public ResponseEntity findUser(@PathVariable String uuid) {
        return new ResponseEntity(findUserAccountUseCase.findAccountUser(uuid), HttpStatus.OK);
    }


}
