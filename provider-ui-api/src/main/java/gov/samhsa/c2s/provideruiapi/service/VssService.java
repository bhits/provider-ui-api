package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.service.dto.ValueSetCategoryDto;

import java.util.List;
import java.util.Locale;

public interface VssService {
    List<ValueSetCategoryDto> getValueSetCategories(Locale locale);
}
