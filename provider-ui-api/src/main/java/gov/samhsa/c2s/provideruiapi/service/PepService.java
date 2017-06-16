package gov.samhsa.c2s.provideruiapi.service;


import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface PepService {
    AccessResponseDto accessDocument(MultipartFile file,
                                     String recipientNpi,
                                     String intermediaryNpi,
                                     String purposeOfUse,
                                     String patientIdRoot,
                                     String patientIdExtension,
                                     String documentEncoding);
}
