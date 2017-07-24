package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentAttestationDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentRevocationDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.IdentifiersDto;

import java.util.Locale;

public interface PcmService {
    Object getProviders(String mrn);

    void saveProviders(String mrn, IdentifiersDto providerIdentifiersDto);

    void deleteProvider(String mrn, Long providerId);

    Object getPurposes();

    Object getConsents(String mrn, Integer page, Integer size);

    Object getConsent(String mrn, Long consentId, String format);

    void saveConsent(String mrn, ConsentDto consentDto, Locale locale);

    void updateConsent(String mrn, Long consentId, ConsentDto consentDto);

    void deleteConsent(String mrn, Long consentId);

    Object getAttestedConsent(String mrn, Long consentId, String format);

    Object getRevokedConsent(String mrn, Long consentId, String format);

    void attestConsent(String mrn, Long consentId, ConsentAttestationDto consentAttestationDto);

    void revokeConsent(String mrn, Long consentId, ConsentRevocationDto consentRevocationDto);

    Object getConsentAttestationTerm(Locale locale);

    Object getConsentRevocationTerm(Locale locale);

    Object getConsentActivities(String mrn, Integer page, Integer size);
}
