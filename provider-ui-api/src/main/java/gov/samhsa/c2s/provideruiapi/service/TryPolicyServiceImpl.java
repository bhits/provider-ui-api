package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.TryPolicyClient;
import gov.samhsa.c2s.provideruiapi.service.util.UserInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TryPolicyServiceImpl implements TryPolicyService {

    @Autowired
    private TryPolicyClient tryPolicyClient;

    @Override
    public Object getSampleDocuments() {
        return tryPolicyClient.getSampleDocuments();
    }

    @Override
    public Object tryPolicyXHTMLWithSampleDoc(String patientId, String consentId, int documentId, String purposeOfUseCode) {
        return tryPolicyClient.tryPolicyXHTMLWithSampleDoc(patientId, consentId, documentId, purposeOfUseCode, UserInfoHelper.getSelectedLocale());
    }
}
