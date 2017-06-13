package gov.samhsa.c2s.provideruiapi.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private String userLocale;
    private List<BaseUmsLookupDto> supportedLocales;
    private String username;
    private String lastName;
    private String firstName;
}
