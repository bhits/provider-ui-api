package gov.samhsa.c2s.provideruiapi.service;

import java.util.Locale;

public interface TryPolicyService {
    Object getSampleDocuments();

    Object tryPolicyXHTMLWithSampleDoc(String patientId, String consentId, String purposeOfUseCode, String indexOfDocuments, Locale locale);
}
