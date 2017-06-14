package gov.samhsa.c2s.provideruiapi.infrastructure;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ValueSetCategoryDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Locale;

@FeignClient("vss")
public interface VssClient {
    @RequestMapping(value = "/valueSetCategories", method = RequestMethod.GET)
    List<ValueSetCategoryDto> getValueSetCategories(@RequestHeader("Accept-Language") Locale locale);
}