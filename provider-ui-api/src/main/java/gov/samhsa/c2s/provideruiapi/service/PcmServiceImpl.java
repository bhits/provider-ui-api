package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.config.ProviderUiProperties;
import gov.samhsa.c2s.provideruiapi.infrastructure.PcmClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentAttestationDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentRevocationDto;
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
    private final ProviderUiProperties providerUiProperties;

    private static final boolean CREATED_BY_PATIENT = false;
    private static final boolean ATTESTED_BY_PATIENT = false;
    private static final boolean REVOKED_BY_PATIENT = false;

    @Autowired
    public PcmServiceImpl(PcmClient pcmClient, JwtTokenExtractor jwtTokenExtractor, ProviderUiProperties providerUiProperties) {
        this.pcmClient = pcmClient;
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.providerUiProperties = providerUiProperties;
    }

    @Override
    public List<Object> getProviders(String mrn) {
        return pcmClient.getProviders(mrn);
    }

    @Override
    public void saveProviders(String mrn, IdentifiersDto providerIdentifiersDto) {
        pcmClient.saveProviders(mrn, providerIdentifiersDto);
    }

    @Override
    public void deleteProvider(String mrn, Long providerId) {
        pcmClient.deleteProvider(mrn, providerId);
    }

    @Override
    public List<Object> getPurposes() {
        return pcmClient.getPurposes();
    }

    @Override
    public PageableDto<Object> getConsents(String mrn, Integer page, Integer size) {
        return pcmClient.getConsents(mrn, page, size);
    }

    @Override
    public Object getConsent(String mrn, Long consentId, String format) {
        return pcmClient.getConsent(mrn, consentId, format);
    }

    @Override
    public void saveConsent(String mrn, ConsentDto consentDto, Locale locale) {
        // Get current user authId
        String createdBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);

        pcmClient.saveConsent(mrn, consentDto, locale, createdBy, CREATED_BY_PATIENT);
    }

    @Override
    public void updateConsent(String mrn, Long consentId, ConsentDto consentDto) {
        // Get current user authId
        String lastUpdatedBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        pcmClient.updateConsent(mrn, consentId, consentDto, lastUpdatedBy);

    }

    @Override
    public void deleteConsent(String mrn, Long consentId) {
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

    @Override
    public void attestConsent(String mrn, Long consentId, ConsentAttestationDto consentAttestationDto) {
        // Get current user authId
        String attestedBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        pcmClient.attestConsent(mrn, consentId, consentAttestationDto, attestedBy, ATTESTED_BY_PATIENT);
    }

    @Override
    public void revokeConsent(String mrn, Long consentId, ConsentRevocationDto consentRevocationDto) {
        // Get current user authId
        String revokedBy = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
        pcmClient.revokeConsent(mrn, consentId, consentRevocationDto, revokedBy, REVOKED_BY_PATIENT);
    }

    @Override
    public Object getConsentAttestationTerm(Locale locale) {
        return pcmClient.getConsentAttestationTerm(providerUiProperties.getConsentManagement().getActiveAttestationTermId(), locale);
    }

    @Override
    public Object getConsentRevocationTerm(Locale locale) {
        return pcmClient.getConsentRevocationTerm(providerUiProperties.getConsentManagement().getActiveRevocationTermId(), locale);
    }

}