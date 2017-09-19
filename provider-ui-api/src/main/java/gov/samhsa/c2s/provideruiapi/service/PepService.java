package gov.samhsa.c2s.provideruiapi.service;


import org.springframework.web.multipart.MultipartFile;

public interface PepService {
    Object accessDocument(MultipartFile file,
                                     String recipientNpi,
                                     String intermediaryNpi,
                                     String purposeOfUse,
                                     String patientIdRoot,
                                     String patientIdExtension,
                                     String documentEncoding);
}
