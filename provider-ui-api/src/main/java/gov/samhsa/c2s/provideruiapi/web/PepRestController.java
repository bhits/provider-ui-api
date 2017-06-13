package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.service.UmsService;
import gov.samhsa.c2s.provideruiapi.service.dto.SegmentedDocument;
import gov.samhsa.c2s.provideruiapi.service.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static gov.samhsa.c2s.provideruiapi.infrastructure.UmsClient.*;

@RestController
@RequestMapping("pep")
public class PepRestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/segmentDocument")
    @ResponseStatus(HttpStatus.CREATED)
    public SegmentedDocument registerUser(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "recipientNpi") String recipientNpi,
                             @RequestParam(value = "intermediaryNpi") String intermediaryNpi,
                             @RequestParam(value = "purposeOfUse") String purposeOfUse,
                             @RequestParam(value = "patientIdRoot") String patientIdRoot,
                             @RequestParam(value = "patientIdExtension") String patientIdExtension,
                             @RequestParam(value = "documentEncoding") String documentEncoding) {
        SegmentedDocument SegmentedDocument = new SegmentedDocument();
        try {
            SegmentedDocument.setDocument(file.getBytes());
        } catch (Exception e) {
            logger.warn( e.getMessage());
        }

        return SegmentedDocument;
    }

}
