package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.PlsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static gov.samhsa.c2s.provideruiapi.infrastructure.PlsClient.*;

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
}