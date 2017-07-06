package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.service.TryPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/try-policy")
public class TryPolicyRestController {

    @Autowired
    private TryPolicyService tryPolicyService;

    @GetMapping("/sampleDocuments")
    public Object getSampleDocuments() {
        return tryPolicyService.getSampleDocuments();
    }

    @GetMapping("/tryPolicySampleXHTML/{patientId}/{consentId}")
    public Object tryPolicyXHTMLWithSampleDoc(@PathVariable String patientId,
                                              @PathVariable String consentId,
                                              @RequestParam String purposeOfUseCode,
                                              @RequestParam String indexOfDocuments,
                                              @RequestHeader("Accept-Language") Locale locale) {
        return tryPolicyService.tryPolicyXHTMLWithSampleDoc(patientId, consentId, purposeOfUseCode, indexOfDocuments, locale);
    }
}