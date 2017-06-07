package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.PcmClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.IdentifiersDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PcmServiceImpl implements PcmService {
    private final PcmClient pcmClient;

    @Autowired
    public PcmServiceImpl(PcmClient pcmClient) {
        this.pcmClient = pcmClient;
    }

    @Override
    public List<Object> getProviders(String mrn) {
        return pcmClient.getProviders(mrn);
    }

    @Override
    public void saveProviders(String mrn, IdentifiersDto providerIdentifiersDto) {
        //TODO: Assert the current provider is authorized to manage consents for mrn
        pcmClient.saveProviders(mrn, providerIdentifiersDto);
    }

    @Override
    public void deleteProvider(String mrn, Long providerId) {
        //TODO: Assert the current provider is authorized to manage consents for mrn
        pcmClient.deleteProvider(mrn, providerId);
    }

    @Override
    public List<Object> getPurposes() {
        return pcmClient.getPurposes();
    }

    @Override
    public PageableDto<Object> getConsents(String mrn, Optional<Long> purposeOfUse, Optional<Long> fromProvider, Optional<Long> toProvider, Integer page, Integer size) {
        //TODO: Assert the current provider is authorized to manage consents for mrn
        return pcmClient.getConsents(mrn, purposeOfUse, fromProvider, toProvider, page, size);
    }

    @Override
    public Object getConsent(String mrn, Long consentId, String format) {
        //TODO: Assert the current provider is authorized to manage consents for mrn
        return pcmClient.getConsent(mrn, consentId, format);
    }
}