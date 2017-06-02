package gov.samhsa.c2s.provideruiapi.infrastructure;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.service.dto.DetailedConsentDto;
import gov.samhsa.c2s.provideruiapi.service.dto.PurposeDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient("pcm")
public interface PcmClient {

    @RequestMapping(value = "/patients/{patientId}/consents", method = RequestMethod.GET)
    PageableDto<DetailedConsentDto> getConsents(@PathVariable("patientId") String patientId,
                                                @RequestParam(value = "purposeOfUse") Optional<Long> purposeOfUse,
                                                @RequestParam(value = "fromProvider") Optional<Long> fromProvider,
                                                @RequestParam(value = "toProvider") Optional<Long> toProvider,
                                                @RequestParam(value = "page", required = false) Integer page,
                                                @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/patients/{patientId}/consents/{consentId}", method = RequestMethod.GET)
    Object getConsent(@PathVariable("patientId") String patientId,
                      @PathVariable("consentId") Long consentId,
                      @RequestParam(value = "format", required = false) String format);

    @RequestMapping(value = "/purposes", method = RequestMethod.GET)
    List<PurposeDto> getPurposes();
}
