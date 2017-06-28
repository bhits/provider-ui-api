package gov.samhsa.c2s.provideruiapi.infrastructure.dto;

import gov.samhsa.c2s.common.validator.constraint.PresentOrFuture;
import lombok.Data;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ScriptAssert(
        lang = "javascript",
        alias = "_",
        script = "_.startDate != null && _.endDate != null && _.startDate.isBefore(_.endDate)",
        message = "consent end date must be after consent start date")
public class ConsentDto {
    private Long id;
    @NotNull
    @Future
    private LocalDate endDate;
    @Valid
    @NotNull
    private IdentifiersDto fromProviders;
    @Valid
    @NotNull
    private IdentifiersDto purposes;
    @Valid
    @NotNull
    private IdentifiersDto sensitivityCategories;
    @NotNull
    @PresentOrFuture
    private LocalDate startDate;
    @Valid
    @NotNull
    private IdentifiersDto toProviders;

    @NotNull
    private boolean shareSensitivityCategoriesEnabled;
}