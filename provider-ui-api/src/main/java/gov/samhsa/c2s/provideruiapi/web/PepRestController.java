package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessRequestDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessResponseDto;
import gov.samhsa.c2s.provideruiapi.service.PepService;
import gov.samhsa.c2s.provideruiapi.service.dto.SegmentedDocument;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("pep")
@Slf4j
public class PepRestController {
    private final PepService pepService;

    public PepRestController(PepService pepService) {
        this.pepService = pepService;
    }

    @PostMapping("/segmentDocument")
    @ResponseStatus(HttpStatus.CREATED)
    public SegmentedDocument registerUser(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "recipientNpi") String recipientNpi,
                             @RequestParam(value = "intermediaryNpi") String intermediaryNpi,
                             @RequestParam(value = "purposeOfUse") String purposeOfUse,
                             @RequestParam(value = "patientIdRoot") String patientIdRoot,
                             @RequestParam(value = "patientIdExtension") String patientIdExtension,
                             @RequestParam(value = "documentEncoding") String documentEncoding) {
        SegmentedDocument segmentedDocument = new SegmentedDocument();
        try {
            segmentedDocument.setDocument(file.getBytes());
        } catch (Exception e) {
            log.warn( e.getMessage());
        }

        return segmentedDocument;
    }

    @RequestMapping(value = "/access", method = RequestMethod.POST)
    public AccessResponseDto access(@Valid @RequestBody AccessRequestDto accessRequest, Principal principal) {
        return pepService.accessDocument(accessRequest);
    }


}
