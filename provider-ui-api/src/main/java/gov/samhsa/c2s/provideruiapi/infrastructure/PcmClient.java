package gov.samhsa.c2s.provideruiapi.infrastructure;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.IdentifiersDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient("pcm")
public interface PcmClient {

    @RequestMapping(value = "/patients/{patientId}/providers", method = RequestMethod.GET)
    List<Object> getProviders(@PathVariable("patientId") String patientId);

    @RequestMapping(value = "/patients/{patientId}/providers", method = RequestMethod.POST)
    void saveProviders(@PathVariable("patientId") String patientId,
                       @Valid @RequestBody IdentifiersDto providerIdentifiersDto);

    @RequestMapping(value = "/patients/{patientId}/providers/{providerId}", method = RequestMethod.DELETE)
    void deleteProvider(@PathVariable("patientId") String patientId,
                        @PathVariable("providerId") Long providerId);

    @RequestMapping(value = "/purposes", method = RequestMethod.GET)
    List<Object> getPurposes();
}