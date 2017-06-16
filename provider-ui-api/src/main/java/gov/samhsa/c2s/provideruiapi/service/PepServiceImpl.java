package gov.samhsa.c2s.provideruiapi.service;


import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessRequestDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessResponseDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PatientIdDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PepClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.SubjectPurposeOfUse;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.XacmlRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class PepServiceImpl implements PepService {

    private final PepClient pepClient;

    public PepServiceImpl(PepClient pepClient) {
        this.pepClient = pepClient;
    }

    @Override
    public AccessResponseDto accessDocument(MultipartFile file,
                                            String recipientNpi,
                                            String intermediaryNpi,
                                            String purposeOfUse,
                                            String patientIdRoot,
                                            String patientIdExtension,
                                            String documentEncoding) {
        AccessRequestDto accessRequest = new AccessRequestDto();
        try {
            accessRequest.setDocument(file.getBytes());
            XacmlRequestDto xacmlRequestDto = new XacmlRequestDto();
            xacmlRequestDto.setIntermediaryNpi(intermediaryNpi);
            xacmlRequestDto.setRecipientNpi(recipientNpi);
            xacmlRequestDto.setPatientId(new PatientIdDto(patientIdRoot, patientIdExtension));
            xacmlRequestDto.setPurposeOfUse(SubjectPurposeOfUse.fromPurposeFhir(purposeOfUse));
            accessRequest.setXacmlRequest(xacmlRequestDto);
            accessRequest.setDocumentEncoding(Optional.of(documentEncoding));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pepClient.access(accessRequest);
    }
}
