package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.service.dto.MrnCodeSystemDto;
import gov.samhsa.c2s.provideruiapi.service.dto.UserCreationLookupDto;

public interface UmsLookupService {
    UserCreationLookupDto getUserCreationLookupInfo();

    MrnCodeSystemDto getMrnCodeSystem();
}
