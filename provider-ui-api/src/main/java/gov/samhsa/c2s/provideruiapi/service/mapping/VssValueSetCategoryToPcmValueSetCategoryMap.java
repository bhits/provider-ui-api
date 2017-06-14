package gov.samhsa.c2s.provideruiapi.service.mapping;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ValueSetCategoryDto;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class VssValueSetCategoryToPcmValueSetCategoryMap extends PropertyMap<ValueSetCategoryDto, gov.samhsa.c2s.provideruiapi.service.dto.ValueSetCategoryDto> {

    @Override
    protected void configure() {
        map().setDisplayName(source.getName());
    }
}