package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.config.ProviderUiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigRestController {

    private final ProviderUiProperties providerUiProperties;

    @Autowired
    public ConfigRestController(ProviderUiProperties providerUiProperties) {
        this.providerUiProperties = providerUiProperties;
    }

    //TODO: Remove it once wrap inside uaa call
    @GetMapping("/config/basicAuthorizationHeader")
    public ProviderUiProperties.Oauth2 getConfig() {
        return providerUiProperties.getOauth2();
    }

    @GetMapping("/config/providerPermissions")
    public ProviderUiProperties.ProviderPermissions getProviderPermissionsConfig() {
        return providerUiProperties.getProviderPermissions();
    }

    @GetMapping("/config")
    public ProviderUiProperties getProviderConfig() {
        return providerUiProperties;
    }
}