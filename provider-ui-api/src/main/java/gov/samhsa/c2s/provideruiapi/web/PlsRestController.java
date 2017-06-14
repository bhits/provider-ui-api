package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.PlsClient;
import gov.samhsa.c2s.provideruiapi.service.dto.ProviderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static gov.samhsa.c2s.provideruiapi.infrastructure.PlsClient.Projection;
import static gov.samhsa.c2s.provideruiapi.infrastructure.PlsClient.X_FORWARDED_HOST;
import static gov.samhsa.c2s.provideruiapi.infrastructure.PlsClient.X_FORWARDED_PORT;
import static gov.samhsa.c2s.provideruiapi.infrastructure.PlsClient.X_FORWARDED_PREFIX;
import static gov.samhsa.c2s.provideruiapi.infrastructure.PlsClient.X_FORWARDED_PROTO;

@RestController
@RequestMapping("/pls")
public class PlsRestController {

    @Autowired
    private PlsClient plsClient;

    @GetMapping("/providers/search/query")
    public Object searchProviders(
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "zipcode", required = false) String zipCode,
            @RequestParam(value = "firstname", required = false) String firstName,
            @RequestParam(value = "lastname", required = false) String lastName,
            @RequestParam(value = "gendercode", required = false) String genderCode,
            @RequestParam(value = "orgname", required = false) String orgName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "projection", defaultValue = Projection.FLATTEN_SMALL_PROVIDER) String projection,
            @RequestHeader(X_FORWARDED_PROTO) String xForwardedProto,
            @RequestHeader(X_FORWARDED_HOST) String xForwardedHost,
            @RequestHeader(X_FORWARDED_PREFIX) String xForwardedPrefix,
            @RequestHeader(X_FORWARDED_PORT) int xForwardedPort) {
        return plsClient.searchProviders(state, city, zipCode, firstName, lastName, genderCode,
                orgName, phone, page, size, sort, projection, xForwardedProto, xForwardedHost, xForwardedPrefix.concat("/pls"), xForwardedPort);
    }

    @GetMapping("/providers/{npi}")
    public List<ProviderDto> searchProviderByNpi(@PathVariable String npi) {
        List<ProviderDto> providers = new ArrayList<>();

        ProviderDto providerDto = new ProviderDto();
        providerDto.setId("11111111");
        providerDto.setFirstName("MICHAEL1");
        providerDto.setLastName("KEMMER1");
        providerDto.setMiddleName(null);
        providerDto.setProviderType("PRACTITIONER");
        providerDto.setDeletable(false);

        ProviderDto providerDto1 = new ProviderDto();
        providerDto1.setId("11112222");
        providerDto1.setFirstName("MICHAEL2");
        providerDto1.setLastName("KEMMER2");
        providerDto1.setMiddleName(null);
        providerDto1.setProviderType("PRACTITIONER");
        providerDto1.setDeletable(false);

        if (providerDto1.getId().equals(npi)) {
            providers.add(providerDto1);
        } else if (providerDto.getId().equals(npi)) {
            providers.add(providerDto);
        }
        return providers;
    }
}