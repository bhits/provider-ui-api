package gov.samhsa.c2s.provideruiapi.service;

public interface TryPolicyService {
    Object getSampleDocuments();

    Object tryPolicyXHTMLWithSampleDoc(String patientId, String consentId, String documentId, String purposeOfUseCode);
}
