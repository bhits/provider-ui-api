package gov.samhsa.c2s.provideruiapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "c2s.provider-ui-api")
@Data
public class ProviderUiApiProperties {

    @NotNull
    @Valid
    private ConsentManagement consentManagement;

    @Data
    public static class ConsentManagement {
        @NotNull
        private Long activeAttestationTermId;

        @NotNull
        private Long activeRevocationTermId;
    }
}