package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.service.PepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("pep")
@Slf4j
public class PepRestController {
    private final PepService pepService;

    public PepRestController(PepService pepService) {
        this.pepService = pepService;
    }

    @RequestMapping(value = "/access", method = RequestMethod.POST)
    public Object access(@RequestParam(value = "file") MultipartFile file,
                                    @RequestParam(value = "recipientNpi") String recipientNpi,
                                    @RequestParam(value = "intermediaryNpi") String intermediaryNpi,
                                    @RequestParam(value = "purposeOfUse") String purposeOfUse,
                                    @RequestParam(value = "patientIdRoot") String patientIdRoot,
                                    @RequestParam(value = "patientIdExtension") String patientIdExtension,
                                    @RequestParam(value = "documentEncoding") String documentEncoding) {

        return pepService.accessDocument( file, recipientNpi,intermediaryNpi, purposeOfUse, patientIdRoot, patientIdExtension,documentEncoding);
    }


}
