package gov.samhsa.c2s.provideruiapi.service.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class SensitivityCategoryDto {
    private String description;
    private String display;
    @NotNull
    private Long id;

    @Valid
    @NotNull
    private IdentifierDto identifier;
}