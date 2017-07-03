package gov.samhsa.c2s.provideruiapi.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

@Component
@ConfigurationProperties(prefix = "c2s.provider-ui")
@Data
public class ProviderUiProperties {
    @NotNull
    @Valid
    private Oauth2 oauth2;

    @Valid
    private ProviderPermissions providerPermissions;

    @Data
    public static class ProviderPermissions {
        private boolean consentSignEnabled;
        private boolean consentRevokeEnabled;
    }

    @Data
    public static class Oauth2 {
        @Valid
        private Client client;

        @Data
        public static class Client {
            @JsonIgnore
            @NotEmpty
            private String clientId;

            @JsonIgnore
            @NotEmpty
            private String secret;

            public byte[] getBasicAuthorizationHeader() {
                return (clientId.concat(":") + secret).getBytes(StandardCharsets.UTF_8);
            }
        }
    }
}