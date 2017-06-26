package gov.samhsa.c2s.provideruiapi.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareSensitivityCategoriesDto {
    @NotNull
    private boolean shareSensitivityCategoriesEnabled;
}
