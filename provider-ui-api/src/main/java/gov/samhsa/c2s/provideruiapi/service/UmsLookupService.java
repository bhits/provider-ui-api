package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.service.dto.UserCreationLookupDto;

import java.util.Locale;

public interface UmsLookupService {
    UserCreationLookupDto getUserCreationLookupInfo();
}
