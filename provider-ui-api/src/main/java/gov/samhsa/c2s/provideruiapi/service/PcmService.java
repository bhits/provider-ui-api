package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.IdentifiersDto;

import java.util.List;

public interface PcmService {
    List<Object> getProviders(String mrn);

    void saveProviders(String mrn, IdentifiersDto providerIdentifiersDto);

    void deleteProvider(String mrn, Long providerId);

    List<Object> getPurposes();
}
