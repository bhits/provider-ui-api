package gov.samhsa.c2s.provideruiapi.infrastructure.dto;

import lombok.Data;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class IdentifierSystemDto {
    private String system;
    private String display;
    private String oid;
    @Valid
    private Map<String, List<RequiredIdentifierSystem>> requiredIdentifierSystemsByRole = new HashMap<>();
}
