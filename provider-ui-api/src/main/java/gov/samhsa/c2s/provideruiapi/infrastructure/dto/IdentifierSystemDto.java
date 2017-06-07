package gov.samhsa.c2s.provideruiapi.infrastructure.dto;

import lombok.Data;

@Data
public class IdentifierSystemDto {
    private String system;
    private String display;
    private String oid;
    private boolean systemGenerated;
}
