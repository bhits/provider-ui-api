package gov.samhsa.c2s.provideruiapi.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MrnCodeSystemDto {
    private String mrnCodeSystem;
}
