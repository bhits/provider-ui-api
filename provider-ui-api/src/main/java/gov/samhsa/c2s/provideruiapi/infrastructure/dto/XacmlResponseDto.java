package gov.samhsa.c2s.provideruiapi.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XacmlResponseDto {
    @NotBlank
    private String pdpDecision;

    @NotNull
    @Singular
    private List<String> pdpObligations = new ArrayList<>();
}
