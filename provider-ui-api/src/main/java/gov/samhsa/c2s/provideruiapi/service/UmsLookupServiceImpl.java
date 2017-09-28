package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.UmsLookupClient;
import gov.samhsa.c2s.provideruiapi.service.dto.MrnCodeSystemDto;
import gov.samhsa.c2s.provideruiapi.service.dto.UserCreationLookupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UmsLookupServiceImpl implements UmsLookupService {

    @Autowired
    private UmsLookupClient umsLookupClient;

    @Override
    public UserCreationLookupDto getUserCreationLookupInfo() {
        Locale locale = LocaleContextHolder.getLocale();
        return UserCreationLookupDto.builder()
                .genderCodes(umsLookupClient.getGenderCodes(locale))
                .stateCodes(umsLookupClient.getStateCodes())
                .countryCodes(umsLookupClient.getCountryCodes())
                .locales(umsLookupClient.getLocales())
                .roles(umsLookupClient.getRoles(locale))
                .identifierSystems(umsLookupClient.getIdentifierSystem())
                .build();
    }

    @Override
    public MrnCodeSystemDto getMrnCodeSystem() {
        return MrnCodeSystemDto.builder()
                .mrnCodeSystem( umsLookupClient.getMrnCodeSystem())
                .build();
    }
}
