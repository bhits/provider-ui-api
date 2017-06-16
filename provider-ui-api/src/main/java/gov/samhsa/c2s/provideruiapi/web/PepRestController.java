package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.service.dto.SegmentedDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        SegmentedDocument segmentedDocument = new SegmentedDocument();
        try {
            segmentedDocument.setDocument(file.getBytes());
        } catch (Exception e) {
            logger.warn( e.getMessage());
        }

        return segmentedDocument;
    }

}
