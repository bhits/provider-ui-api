package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.PlsClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.FlattenedSmallProviderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlsServiceImpl implements PlsService {
    private static final int STATE_NUMBER = 52;

    @Autowired
    PlsClient plsClient;

    @Override
    public Object searchProviders(String state, String city, String zipCode,
                                  String firstName, String lastName, String genderCode,
                                  String orgName, String phone, String page,
                                  String size, String sort, String projection,
                                  String xForwardedProto, String xForwardedHost,
                                  String xForwardedPrefix, String xForwardedPort) {

        return plsClient.searchProviders(state, city, zipCode,
                firstName, lastName, genderCode,
                orgName, phone, page,
                size, sort, PlsClient.Projection.FLATTEN_SMALL_PROVIDER,
                xForwardedProto, xForwardedHost, xForwardedPrefix.concat("/pls"),
                xForwardedPort);
    }

    @Override
    public FlattenedSmallProviderDto searchProviderByNpi(String npi) {
        return plsClient.searchByNpi(npi);
    }

    @Override
    public Object getStateCodes() {
        return plsClient.getStateCodes(STATE_NUMBER, 0);
    }
}
