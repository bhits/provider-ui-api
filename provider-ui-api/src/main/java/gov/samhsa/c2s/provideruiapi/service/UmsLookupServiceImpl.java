package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.config.ProviderUiProperties;
import gov.samhsa.c2s.provideruiapi.infrastructure.UmsLookupClient;
import gov.samhsa.c2s.provideruiapi.service.dto.UserCreationLookupDto;
import gov.samhsa.c2s.provideruiapi.service.dto.UserSearchConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UmsLookupServiceImpl implements UmsLookupService {

    @Autowired
    private UmsLookupClient umsLookupClient;

    @Autowired
    private ProviderUiProperties providerUiProperties;

    @Override
    public UserCreationLookupDto getUserCreationLookupInfo() {
        return UserCreationLookupDto.builder()
                .genderCodes(umsLookupClient.getGenderCodes())
                .stateCodes(umsLookupClient.getStateCodes())
                .countryCodes(umsLookupClient.getCountryCodes())
                .locales(umsLookupClient.getLocales())
                .roles(umsLookupClient.getRoles())
                .identifierSystems(umsLookupClient.getIdentifierSystem())
                .build();
    }

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
