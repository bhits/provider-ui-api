package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.service.PcmService;
import gov.samhsa.c2s.provideruiapi.service.dto.DetailedConsentDto;
import gov.samhsa.c2s.provideruiapi.service.dto.PurposeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pcm")
public class PcmRestController {

    private final PcmService pcmService;

    @Autowired
    public PcmRestController(PcmService pcmService) {
        this.pcmService = pcmService;
    }

    @GetMapping("/patients/{mrn}/consents")
    public PageableDto<DetailedConsentDto> getConsents(@PathVariable String mrn,
                                                       @RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "size", required = false) Integer size) {
        return pcmService.getConsents(mrn, page, size);
    }

    @GetMapping("/patients/{mrn}/consents/{consentId}")
    public Object getConsent(@PathVariable String mrn,
                             @PathVariable Long consentId,
                             @RequestParam(value = "format", required = false) String format) {
        return pcmService.getConsent(mrn, consentId, format);
    }

    @GetMapping("/purposes")
    public List<PurposeDto> getPurposes() {
        return pcmService.getPurposes();
    }

 }
