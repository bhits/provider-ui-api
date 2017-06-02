package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.service.dto.DetailedConsentDto;
import gov.samhsa.c2s.provideruiapi.service.dto.PurposeDto;

import java.util.List;
import java.util.Optional;

public interface PcmService {

    PageableDto<DetailedConsentDto> getConsents(String mrn, Optional<Long> purposeOfUse, Optional<Long> fromProvider, Optional<Long> toProvider, Integer page, Integer size);

    Object getConsent(String mrn, Long consentId, String format);

    List<PurposeDto> getPurposes();

}
