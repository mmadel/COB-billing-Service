package com.cob.billing.usecases.security.kc;

import com.cob.billing.exception.business.UserException;
import com.cob.billing.usecases.security.DecryptPasswordUseCase;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;


import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class CreateUserCredentialsUseCase {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthorizedKeyCloakAdminUseCase authorizedKeyCloakAdminUseCase;
    @Autowired
    private DecryptPasswordUseCase decryptPasswordUseCase;
    @Value("${kc.url}")
    private String keycloakURL;
    @Value("${kc.realm}")
    private String realm;

    public void create(String userUUID, String password) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UserException {
        HttpHeaders requestHeaders = createHHTPHeader();
        CredentialRepresentation credential = createCredentialRepresentation(password);
        Gson gson = new Gson();
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(credential).toString(), requestHeaders);
        try {
            restTemplate.put(keycloakURL + "/admin/realms/" + realm + "/users/" + userUUID + "/reset-password", httpEntity);
        } catch (HttpClientErrorException.BadRequest exception) {
            String message = exception.getMessage().split(":")[4].split("\"")[0];
            throw new UserException(HttpStatus.BAD_REQUEST, UserException.INVALID_PASSWORD, new Object[]{message});
        }
    }

    private HttpHeaders createHHTPHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        authorizedKeyCloakAdminUseCase.authorizeAdministrator();
        headers.setBearerAuth(authorizedKeyCloakAdminUseCase.getAccessToken());
        return headers;
    }

    private CredentialRepresentation createCredentialRepresentation(String password) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(decryptPasswordUseCase.decrypt(password));
        Gson gson = new Gson();
        gson.toJson(credential);
        return credential;
    }
}
