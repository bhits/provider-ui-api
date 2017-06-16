package gov.samhsa.c2s.provideruiapi.service;


import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessRequestDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessResponseDto;

public interface PepService {
    AccessResponseDto accessDocument(AccessRequestDto accessRequest);
}
