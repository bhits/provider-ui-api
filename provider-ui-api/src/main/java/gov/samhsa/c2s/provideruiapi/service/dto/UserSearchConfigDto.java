package gov.samhsa.c2s.provideruiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchConfigDto {
    private boolean firstNameSearchEnabled;
    private boolean lastNameSearchEnabled;
    private boolean dateOfBirthSearchEnabled;
    private boolean genderSearchEnabled;
    private boolean patientIdSearchEnabled;
}
