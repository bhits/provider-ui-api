package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.service.dto.UserCreationLookupDto;
import gov.samhsa.c2s.provideruiapi.service.dto.UserSearchConfigDto;

public interface UmsLookupService {
    UserCreationLookupDto getUserCreationLookupInfo();
    UserSearchConfigDto getUserSearchConfig();
}
