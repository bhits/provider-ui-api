package gov.samhsa.c2s.provideruiapi.infrastructure;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentAttestationDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentRevocationDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.IdentifiersDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

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

    @RequestMapping(value = "/patients/{patientId}/consents", method = RequestMethod.GET)
    PageableDto<Object> getConsents(@PathVariable("patientId") String patientId,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/patients/{patientId}/consents/{consentId}", method = RequestMethod.GET)
    Object getConsent(@PathVariable("patientId") String patientId,
                      @PathVariable("consentId") Long consentId,
                      @RequestParam(value = "format", required = false) String format);

    @RequestMapping(value = "/patients/{patientId}/consents/{consentId}/attestation", method = RequestMethod.GET)
    Object getAttestedConsent(@PathVariable("patientId") String patientId,
                              @PathVariable("consentId") Long consentId,
                              @RequestParam(value = "format", required = false) String format);

    @RequestMapping(value = "/patients/{patientId}/consents/{consentId}/revocation", method = RequestMethod.GET)
    Object getRevokedConsent(@PathVariable("patientId") String patientId,
                             @PathVariable("consentId") Long consentId,
                             @RequestParam(value = "format", required = false) String format);

    @RequestMapping(value = "/patients/{patientId}/consents", method = RequestMethod.POST)
    void saveConsent(@PathVariable("patientId") String patientId,
                     @Valid @RequestBody ConsentDto consentDto,
                     @RequestHeader("Accept-Language") Locale locale,
                     @RequestParam(value = "createdBy") String createdBy,
                     @RequestParam(value = "createdByPatient") boolean createdByPatient);

    @RequestMapping(value = "/patients/{patientId}/consents/{consentId}", method = RequestMethod.PUT)
    void updateConsent(@PathVariable("patientId") String patientId,
                       @PathVariable("consentId") Long consentId,
                       @Valid @RequestBody ConsentDto consentDto,
                       @RequestParam(value = "lastUpdatedBy") String lastUpdatedBy);

    @RequestMapping(value = "/patients/{patientId}/consents/{consentId}", method = RequestMethod.DELETE)
    void deleteConsent(@PathVariable("patientId") String patientId,
                       @PathVariable("consentId") Long consentId,
                       @RequestParam(value = "lastUpdatedBy") String lastUpdatedBy);

    @RequestMapping(value = "/patients/{patientId}/consents/{consentId}/attestation", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void attestConsent(@PathVariable("patientId") String patientId,
                       @PathVariable("consentId") Long consentId,
                       @Valid @RequestBody ConsentAttestationDto consentAttestationDto,
                       @RequestParam(value = "attestedBy") String attestedBy,
                       @RequestParam(value = "attestedByPatient") boolean attestedByPatient);

    @RequestMapping(value = "/patients/{patientId}/consents/{consentId}/revocation", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void revokeConsent(@PathVariable("patientId") String patientId,
                       @PathVariable("consentId") Long consentId,
                       @Valid @RequestBody ConsentRevocationDto consentRevocationDto,
                       @RequestParam(value = "revokedBy") String revokedBy,
                       @RequestParam(value = "revokedByPatient") boolean revokedByPatient);

    @RequestMapping(value = "/consentAttestationTerm", method = RequestMethod.GET)
    Object getConsentAttestationTerm(@RequestParam(value = "id", required = false) Long id,
                                             @RequestHeader("Accept-Language") Locale locale);

    @RequestMapping(value = "/consentRevocationTerm", method = RequestMethod.GET)
    Object getConsentRevocationTerm(@RequestParam(value = "id", required = false) Long id,
                                            @RequestHeader("Accept-Language") Locale locale);
}