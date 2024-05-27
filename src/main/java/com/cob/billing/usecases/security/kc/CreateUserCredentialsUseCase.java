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
import org.springframework.web.client.HttpServerErrorException;
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
    RemoveKeycloakUserUseCase removeKeycloakUserUseCase;
    @Autowired
    private DecryptPasswordUseCase decryptPasswordUseCase;
    @Value("${kc.url}")
    private String keycloakURL;
    @Value("${kc.realm}")
    private String realm;

    private final static String RESET_PASSWORD="reset-password";
    private final static String EXECUTE_ACTIONS_EMAIL="execute-actions-email";
    public void create(String userUUID, String password) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UserException {
        HttpHeaders requestHeaders = createHHTPHeader();
        if (password != null)
            resetCredentialRepresentation(password, userUUID, requestHeaders);
        else
            executeActionCredentialRepresentation(userUUID, requestHeaders);
    }

    private HttpHeaders createHHTPHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        authorizedKeyCloakAdminUseCase.authorizeAdministrator();
        headers.setBearerAuth(authorizedKeyCloakAdminUseCase.getAccessToken());
        return headers;
    }

    private void resetCredentialRepresentation(String password, String userUUID, HttpHeaders requestHeaders) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UserException {
        log.info("resetCredentialRepresentation");
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(decryptPasswordUseCase.decrypt(password));
        Gson gson = new Gson();
        gson.toJson(credential);
        createHttpRequest(userUUID, gson.toJson(credential), RESET_PASSWORD, requestHeaders);
    }

    private void executeActionCredentialRepresentation(String userUUID, HttpHeaders requestHeaders) throws UserException {
        log.info("executeActionCredentialRepresentation");
        Gson gson = new Gson();
        String[] actions = new String[]{"UPDATE_PASSWORD"};
        createHttpRequest(userUUID, gson.toJson(actions), EXECUTE_ACTIONS_EMAIL, requestHeaders);
    }

    private void createHttpRequest(String uuid, String body, String action, HttpHeaders requestHeaders) throws UserException {
        HttpEntity<String> httpEntity = new HttpEntity<>(body, requestHeaders);
        try {
            restTemplate.put(keycloakURL + "/admin/realms/" + realm + "/users/" + uuid + "/" + action, httpEntity);
        } catch (HttpServerErrorException.InternalServerError exception) {
            removeKeycloakUserUseCase.remove(uuid);
            String message =exception.getMessage().split("errorMessage\":")[1].replace("}","")
                    .replace("\"","");
            throw new UserException(HttpStatus.BAD_REQUEST, UserException.INVALID_PASSWORD, new Object[]{message});
        }catch (HttpClientErrorException.BadRequest exception){
            removeKeycloakUserUseCase.remove(uuid);
            String message =exception.getMessage().split("error\":")[1].replace("}","")
                    .replace("\"","");
            throw new UserException(HttpStatus.BAD_REQUEST, UserException.INVALID_PASSWORD, new Object[]{message});
        }
    }
}
