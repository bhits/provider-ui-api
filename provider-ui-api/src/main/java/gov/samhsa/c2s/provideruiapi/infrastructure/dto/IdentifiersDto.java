package gov.samhsa.c2s.provideruiapi.infrastructure.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Data
public class IdentifiersDto {
    @Valid
    @NotEmpty
    private Set<IdentifierDto> identifiers = new HashSet<>();
}