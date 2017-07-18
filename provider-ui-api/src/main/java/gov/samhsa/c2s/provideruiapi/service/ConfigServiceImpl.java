package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.config.ProviderUiProperties;
import gov.samhsa.c2s.provideruiapi.infrastructure.UmsLookupClient;
import gov.samhsa.c2s.provideruiapi.service.dto.UserCreationLookupDto;
import gov.samhsa.c2s.provideruiapi.service.dto.UserSearchConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ProviderUiProperties providerUiProperties;

    @Override
    public UserSearchConfigDto getUserSearchConfig() {
        return UserSearchConfigDto.builder()
               .firstNameSearchEnabled(providerUiProperties.getSearch().isFirstNameEnabled())
               .lastNameSearchEnabled(providerUiProperties.getSearch().isLastNameEnabled())
               .dateOfBirthSearchEnabled(providerUiProperties.getSearch().isDateOfBirthEnabled())
               .genderSearchEnabled(providerUiProperties.getSearch().isGenderEnabled())
               .patientIdSearchEnabled(providerUiProperties.getSearch().isPatientIdEnabled())
               .build();
    }
}
