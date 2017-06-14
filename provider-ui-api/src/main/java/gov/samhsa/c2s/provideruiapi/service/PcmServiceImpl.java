package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.PcmClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.IdentifiersDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.service.dto.JwtTokenKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class PcmServiceImpl implements PcmService {
    private final PcmClient pcmClient;
    private final JwtTokenExtractor jwtTokenExtractor;

    @Autowired
    public PcmServiceImpl(PcmClient pcmClient, JwtTokenExtractor jwtTokenExtractor) {
        this.pcmClient = pcmClient;
        this.jwtTokenExtractor = jwtTokenExtractor;
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
    public PageableDto<Object> getConsents(String mrn, Integer page, Integer size) {
        //TODO: Assert the current provider is authorized to manage consents for mrn
        return pcmClient.getConsents(mrn, page, size);
    }

    @Override
    public Object getConsent(String mrn, Long consentId, String format) {
        //TODO: Assert the current provider is authorized to manage consents for mrn
        return pcmClient.getConsent(mrn, consentId, format);
    }

    @Override
    public void saveConsent(String mrn, ConsentDto consentDto, Locale locale) {
        //TODO: Assert the current provider is authorized to manage consents for mrn
        // Get current user authId
        String createdBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);

        pcmClient.saveConsent(mrn, consentDto, locale, createdBy, true);
    }

    @Override
    public void updateConsent(String mrn, Long consentId, ConsentDto consentDto) {
        //TODO: Assert the current provider is authorized to manage consents for mrn

        // Get current user authId
        String lastUpdatedBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        pcmClient.updateConsent(mrn, consentId, consentDto, lastUpdatedBy);

    }

    @Override
    public void deleteConsent(String mrn, Long consentId) {
        //TODO: Assert the current provider is authorized to manage consents for mrn

        // Get current user authId
        String lastUpdatedBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        pcmClient.deleteConsent(mrn, consentId, lastUpdatedBy);
    }

    @Override
    public Object getAttestedConsent(String mrn, Long consentId, String format) {
        return pcmClient.getAttestedConsent(mrn, consentId, format);
    }

    @Override
    public Object getRevokedConsent(String mrn, Long consentId, String format) {
        return pcmClient.getRevokedConsent(mrn, consentId, format);
    }
}