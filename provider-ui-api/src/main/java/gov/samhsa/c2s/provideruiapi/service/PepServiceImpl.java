package gov.samhsa.c2s.provideruiapi.service;


import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessRequestDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessResponseDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PepClient;
import org.springframework.stereotype.Service;

@Service
public class PepServiceImpl implements PepService {

    private final PepClient pepClient;

    public PepServiceImpl(PepClient pepClient) {
        this.pepClient = pepClient;
    }

    @Override
    public AccessResponseDto accessDocument(AccessRequestDto accessRequest) {
        return pepClient.access(accessRequest);
    }
}
