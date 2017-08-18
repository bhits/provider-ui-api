package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.config.ProviderUiApiProperties;
import gov.samhsa.c2s.provideruiapi.infrastructure.UaaClient;
import gov.samhsa.c2s.provideruiapi.service.dto.LoginRequestDto;
import gov.samhsa.c2s.provideruiapi.service.exception.AccountLockedException;
import gov.samhsa.c2s.provideruiapi.service.exception.BadCredentialsException;
import gov.samhsa.c2s.provideruiapi.service.exception.UserUnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UaaServiceImpl implements UaaService {
    private static final String OAUTH2_GRAND_TYPE = "password";
    private static final String OAUTH2_RESPONSE_TYPE = "token";
    private static final String BAD_CREDENTIAL_ERROR_MESSAGE = "Bad credentials";
    private static final String ACCOUNT_LOCKED_ERROR_MESSAGE = "Your account has been locked because of too many failed attempts to login";

    private final ProviderUiApiProperties providerUiApiProperties;
    private final UaaClient uaaClient;

    @Autowired
    public UaaServiceImpl(ProviderUiApiProperties providerUiApiProperties, UaaClient uaaClient) {
        this.providerUiApiProperties = providerUiApiProperties;
        this.uaaClient = uaaClient;
    }

    @Override
    public Object login(LoginRequestDto requestDto) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("client_id", providerUiApiProperties.getOauth2().getClient().getClientId());
        requestParams.put("client_secret", providerUiApiProperties.getOauth2().getClient().getClientSecret());
        requestParams.put("grant_type", OAUTH2_GRAND_TYPE);
        requestParams.put("response_type", OAUTH2_RESPONSE_TYPE);
        requestParams.put("username", requestDto.getUsername());
        requestParams.put("password", requestDto.getPassword());

        try {
            return uaaClient.getTokenUsingPasswordGrant(requestParams);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
            if(errorMessage.contains(BAD_CREDENTIAL_ERROR_MESSAGE)){
                throw new BadCredentialsException();
            }else if(errorMessage.contains(ACCOUNT_LOCKED_ERROR_MESSAGE)){
                throw new AccountLockedException();
            }
        }
        throw new UserUnauthorizedException();
    }
}
