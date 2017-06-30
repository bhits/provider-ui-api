package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.config.ProviderUiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigRestController {

    @Autowired
    private ProviderUiProperties providerUiProperties;

    @GetMapping("/config/basicAuthorizationHeader")
    public ProviderUiProperties getConfig() {
        return providerUiProperties;
    }
}