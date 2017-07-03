package gov.samhsa.c2s.provideruiapi.infrastructure;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@FeignClient("try-policy")
public interface TryPolicyClient {

    @RequestMapping(value = "/sampleDocuments", method = RequestMethod.GET)
    Object getSampleDocuments();

    @RequestMapping(value = "/tryPolicySampleXHTML/{patientId}/{consentId}", method = RequestMethod.GET)
    Object tryPolicyXHTMLWithSampleDoc(@PathVariable("patientId") String patientId,
                                       @PathVariable("consentId") String consentId,
                                       @RequestParam("purposeOfUseCode") String purposeOfUseCode,
                                       @RequestParam("indexOfDocuments") String indexOfDocuments,
                                       @RequestHeader("Accept-Language") Locale locale);
}