package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.PcmClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.service.dto.DetailedConsentDto;
import gov.samhsa.c2s.provideruiapi.service.dto.PurposeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PcmServiceImpl implements PcmService {
    private final PcmClient pcmClient;

    public PcmServiceImpl(PcmClient pcmClient) {
        this.pcmClient = pcmClient;
    }

    @Override
    public PageableDto<DetailedConsentDto> getConsents(String mrn, Optional<Long> purposeOfUse, Optional<Long> fromProvider, Optional<Long> toProvider, Integer page, Integer size) {
        return pcmClient.getConsents(mrn, purposeOfUse, fromProvider, toProvider, page, size);
    }

    @Override
    public Object getConsent(String mrn, Long consentId, String format) {
        return pcmClient.getConsent(mrn, consentId, format);
    }

    @Override
    public List<PurposeDto> getPurposes() {
        return pcmClient.getPurposes();
    }
}
