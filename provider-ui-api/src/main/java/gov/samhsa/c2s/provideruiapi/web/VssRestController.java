package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.service.VssService;
import gov.samhsa.c2s.provideruiapi.service.dto.ValueSetCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/vss")
public class VssRestController {

    @Autowired
    private VssService vssService;

    @GetMapping("/valueSetCategories")
    List<ValueSetCategoryDto> getValueSetCategories(@RequestHeader("Accept-Language") Locale locale) {
        return vssService.getValueSetCategories(locale);
    }
}