package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.VssClient;
import gov.samhsa.c2s.provideruiapi.service.dto.ValueSetCategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class VssServiceImpl implements VssService {

    private final VssClient vssClient;

    private final ModelMapper modelMapper;

    @Autowired
    public VssServiceImpl(VssClient vssClient, ModelMapper modelMapper) {
        this.vssClient = vssClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ValueSetCategoryDto> getValueSetCategories(Locale locale) {
        return vssClient.getValueSetCategories(locale).stream()
                .map(valueSetCategoryDto -> modelMapper.map(valueSetCategoryDto, ValueSetCategoryDto.class))
                .collect(Collectors.toList());
    }
}
