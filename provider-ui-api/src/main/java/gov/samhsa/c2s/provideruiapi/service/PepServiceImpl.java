package gov.samhsa.c2s.provideruiapi.service;


import feign.FeignException;
import gov.samhsa.c2s.provideruiapi.infrastructure.PepClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessRequestDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PatientIdDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.SubjectPurposeOfUse;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.XacmlRequestDto;
import gov.samhsa.c2s.provideruiapi.service.exception.NoDocumentFoundException;
import gov.samhsa.c2s.provideruiapi.service.exception.PepClientInterfaceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class PepServiceImpl implements PepService {

    private final PepClient pepClient;

    private static final boolean GET_SEGMENTED_DOC_IN_HTML_FORMAT = true;

    public PepServiceImpl(PepClient pepClient) {
        this.pepClient = pepClient;
    }

    @Override
    public Object accessDocument(MultipartFile file,
                                            String recipientNpi,
                                            String intermediaryNpi,
                                            String purposeOfUse,
                                            String patientIdRoot,
                                            String patientIdExtension,
                                            String documentEncoding) {
        log.debug("PEP Service accessDocument Start");
        Object pepResponse;
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
            log.debug("Invoking pep feign client - Start");
            pepResponse = pepClient.access(accessRequest, GET_SEGMENTED_DOC_IN_HTML_FORMAT, LocaleContextHolder.getLocale());
            log.debug("Invoking pep feign client - End");
        } catch (FeignException fe) {
            int causedByStatus = fe.status();
            switch (causedByStatus) {
                case 404:
                    log.error("PEP client returned a 404 - NO Consent/Document Found REQUEST status" +
                            " to PEP client", fe);
                    throw new NoDocumentFoundException("Invalid document was passed to DSS client");
                default:
                    log.error("PEP client returned an unexpected instance of FeignException", fe);
                    throw new PepClientInterfaceException("An unknown error occurred while attempting to communicate " +
                            "with" +
                            " PEP service");
            }
        } catch (IOException e) {
            log.error("Unable to Read the input file content", e);
            throw new PepClientInterfaceException("Unable to Read the input file content");
        }

        log.debug("PEP Service accessDocument End");
        return pepResponse;

    }
}
