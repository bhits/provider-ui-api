package gov.samhsa.c2s.provideruiapi.infrastructure;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("try-policy")
public interface TryPolicyClient {

    @RequestMapping(value = "/sampleDocuments", method = RequestMethod.GET)
    Object getSampleDocuments();
}