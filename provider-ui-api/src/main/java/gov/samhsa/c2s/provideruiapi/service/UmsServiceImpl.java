package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.UmsClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ProfileResponse;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.provideruiapi.service.dto.JwtTokenKey;
import gov.samhsa.c2s.provideruiapi.service.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsServiceImpl implements UmsService {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    @Autowired
    private UmsClient umsClient;

    @Autowired
    private ModelMapper modelMapper;

    private String ROLE_CODE="patient";

    @Autowired
    private JwtTokenExtractor jwtTokenExtractor;

    @Override
    public PageableDto<UserDto> getAllUsers(Integer page, Integer size) {
        //Mapping of generic parameterized types
        Type pageableUserDtoType = new TypeToken<PageableDto<UserDto>>() {
        }.getType();

        PageableDto<UmsUserDto> pageableUmsUserDto = umsClient.getAllUsers(page, size, ROLE_CODE);
        List<UserDto> userDtos = pageableUmsUserDto.getContent().stream()
                .map(umsUserDto -> modelMapper.map(umsUserDto, UserDto.class))
                .collect(Collectors.toList());

        PageableDto<UserDto> pageableUserDto = modelMapper.map(pageableUmsUserDto, pageableUserDtoType);
        pageableUserDto.setContent(userDtos);

        return pageableUserDto;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        return modelMapper.map(umsClient.registerUser(modelMapper.map(userDto, UmsUserDto.class)), UserDto.class);
    }

    @Override
    public List<UserDto> searchUsersByFirstNameAndORLastName(String term) {
        return umsClient.searchUsersByFirstNameAndORLastName(term).stream()
                .map(umsUserDto -> modelMapper.map(umsUserDto, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long userId) {
        return modelMapper.map(umsClient.getUser(userId), UserDto.class);
    }

    @Override
    public void updateUser(Long userId, UserDto userDto) {
        umsClient.updateUser(userId, modelMapper.map(userDto, UmsUserDto.class));
    }

    @Override
    public Object initiateUserActivation(Long userId, String xForwardedProto, String xForwardedHost, int xForwardedPort) {
        return umsClient.initiateUserActivation(userId, xForwardedProto, xForwardedHost, xForwardedPort);
    }

    @Override
    public Object getCurrentUserCreationInfo(Long userId) {
        return umsClient.getCurrentUserCreationInfo(userId);
    }

    @Override
    public void disableUser(Long userId) {
        umsClient.disableUser(userId);
    }

    @Override
    public void enableUser(Long userId) {
        umsClient.enableUser(userId);
    }


    @Override
    public ProfileResponse getProviderProfile() {
        //Get system supported Locales
        List<BaseUmsLookupDto> supportedLocales = umsClient.getLocales();

        UmsUserDto umsUserDto=umsClient.getUserById(jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID));
        return ProfileResponse.builder()
                .userLocale(umsUserDto.getLocale())
                .supportedLocales(supportedLocales)
                .firstName(umsUserDto.getFirstName())
                .lastName(umsUserDto.getLastName())
                .build();
    }


    @Override
    public PageableDto<UserDto> searchUsersByDemographic(String firstName,
                                                         String lastName,
                                                         LocalDate birthDate,
                                                         String genderCode,
                                                         String mrn,
                                                         Integer page,
                                                         Integer size) {
        //Mapping of generic parameterized types
        Type pageableUserDtoType = new TypeToken<PageableDto<UserDto>>() {
        }.getType();

        StringBuilder formatBirthday = new StringBuilder();
        if(birthDate!=null) {
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern(DATE_FORMAT);
            formatBirthday.append(birthDate.format(formatters));
        }

        PageableDto<UmsUserDto> pageableUmsUserDto = umsClient.searchUsersByDemographic(firstName,
                lastName,
                genderCode,
                formatBirthday.toString(),mrn, ROLE_CODE,page, size);
        List<UserDto> userDtos = pageableUmsUserDto.getContent().stream()
                .map(umsUserDto -> modelMapper.map(umsUserDto, UserDto.class))
                .collect(Collectors.toList());

        PageableDto<UserDto> pageableUserDto = modelMapper.map(pageableUmsUserDto, pageableUserDtoType);
        pageableUserDto.setContent(userDtos);

        return pageableUserDto;
    }
}
