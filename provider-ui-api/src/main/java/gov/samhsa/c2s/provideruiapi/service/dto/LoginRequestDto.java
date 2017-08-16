package gov.samhsa.c2s.provideruiapi.service.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class LoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
