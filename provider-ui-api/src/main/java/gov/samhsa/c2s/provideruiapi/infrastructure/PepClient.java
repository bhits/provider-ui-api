package gov.samhsa.c2s.provideruiapi.infrastructure;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.AccessRequestDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Locale;

@FeignClient("pep")
public interface PepClient {
    @RequestMapping(value = "/access", method = RequestMethod.POST)
    Object access(@Valid @RequestBody AccessRequestDto accessRequest, @RequestParam(value = "getSegmentedDocumentAsHTML") boolean getSegmentedDocumentAsHTML, @RequestHeader("Accept-Language") Locale locale);
}
