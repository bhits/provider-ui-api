package gov.samhsa.c2s.provideruiapi.service.mapping.customconverter;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.UmsAddressDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.provideruiapi.service.mapping.Use;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class UmsAddressesToHomeAddressConverter extends AbstractConverter<UmsUserDto, UmsAddressDto> {
    @Override
    protected UmsAddressDto convert(UmsUserDto source) {
        return source.getAddresses().stream()
                .filter(umsAddressDto -> umsAddressDto.getUse().equalsIgnoreCase(Use.HOME.toString()))
                .findFirst()
                .orElse(null);
    }
}
