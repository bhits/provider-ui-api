package gov.samhsa.c2s.provideruiapi.service;


import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessRequestDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessResponseDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PatientIdDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PepClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.SubjectPurposeOfUse;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.XacmlRequestDto;
import gov.samhsa.c2s.provideruiapi.service.exception.NoDocumentFoundException;
import gov.samhsa.c2s.provideruiapi.service.exception.PepClientInterfaceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
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

        AccessResponseDto accessResponseDto;
        try {
            accessResponseDto = pepClient.access(accessRequest);

        } catch (HystrixRuntimeException hystrixErr) {
            Throwable causedBy = hystrixErr.getCause();

            if (!(causedBy instanceof FeignException)) {
                log.error("Unexpected instance of HystrixRuntimeException has occurred", hystrixErr);
                throw new PepClientInterfaceException("An unknown error occurred while attempting to communicate with" +
                        " PEP service", hystrixErr);
            }

            int causedByStatus = ((FeignException) causedBy).status();

            switch (causedByStatus) {
                case 404:
                    log.error("PEP client returned a 404 - NO Consent/Document Found REQUEST status" +
                            " to PEP client", causedBy);
                    throw new NoDocumentFoundException("Invalid document was passed to DSS client");
                default:
                    log.error("PEP client returned an unexpected instance of FeignException", causedBy);
                    throw new PepClientInterfaceException("An unknown error occurred while attempting to communicate " +
                            "with" +
                            " PEP service");
            }
        }
        return accessResponseDto;
    }
}
