package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.service.UmsService;
import gov.samhsa.c2s.provideruiapi.service.dto.UserDto;
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

    @PostMapping("/segmentDocument")
    @ResponseStatus(HttpStatus.CREATED)
    public Object registerUser(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "recipientNpi") String recipientNpi,
                             @RequestParam(value = "intermediaryNpi") String intermediaryNpi,
                             @RequestParam(value = "purposeOfUse") String purposeOfUse,
                             @RequestParam(value = "patientIdRoot") String patientIdRoot,
                             @RequestParam(value = "patientIdExtension") String patientIdExtension,
                             @RequestParam(value = "documentEncoding") String documentEncoding) {
        return file;
    }

}
