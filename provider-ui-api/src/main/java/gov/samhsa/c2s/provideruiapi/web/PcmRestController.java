package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ConsentDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.IdentifiersDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.service.PcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/pcm")
public class PcmRestController {

    private final PcmService pcmService;

    @Autowired
    public PcmRestController(PcmService pcmService) {
        this.pcmService = pcmService;
    }

    @GetMapping("/patients/{mrn}/providers")
    public List<Object> getProviders(@PathVariable String mrn) {
        return pcmService.getProviders(mrn);
    }

    @PostMapping("/patients/{mrn}/providers")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProviders(@PathVariable String mrn,
                              @Valid @RequestBody IdentifiersDto providerIdentifiersDto) {
        pcmService.saveProviders(mrn, providerIdentifiersDto);
    }

    @DeleteMapping("/patients/{mrn}/providers/{providerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvider(@PathVariable String mrn,
                               @PathVariable Long providerId) {
        pcmService.deleteProvider(mrn, providerId);
    }

    @GetMapping("/purposes")
    public List<Object> getPurposes() {
        return pcmService.getPurposes();
    }

    @GetMapping("/patients/{mrn}/consents")
    public PageableDto<Object> getConsents(@PathVariable String mrn,
                                           @RequestParam(value = "purposeOfUse") Optional<Long> purposeOfUse,
                                           @RequestParam(value = "fromProvider") Optional<Long> fromProvider,
                                           @RequestParam(value = "toProvider") Optional<Long> toProvider,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size) {
        return pcmService.getConsents(mrn, purposeOfUse, fromProvider, toProvider, page, size);
    }

    @GetMapping("/patients/{mrn}/consents/{consentId}")
    public Object getConsent(@PathVariable String mrn,
                             @PathVariable Long consentId,
                             @RequestParam(value = "format", required = false) String format) {
        return pcmService.getConsent(mrn, consentId, format);
    }

    @PostMapping("/patients/{mrn}/consents")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveConsent(@PathVariable String mrn,
                            @Valid @RequestBody ConsentDto consentDto, @RequestHeader("Accept-Language") Locale locale) {
        pcmService.saveConsent(mrn, consentDto, locale);
    }

    @PutMapping("/consents/{consentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateConsent(@PathVariable String patientId, @PathVariable Long consentId,
                              @Valid @RequestBody ConsentDto consentDto) {
        pcmService.updateConsent(patientId, consentId, consentDto);
    }

    @DeleteMapping("/patients/{mrn}/consents/{consentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsent(@PathVariable String mrn, @PathVariable Long consentId) {
        pcmService.deleteConsent(mrn, consentId);
    }
}