package gov.samhsa.c2s.provideruiapi.service;

import feign.FeignException;
import gov.samhsa.c2s.provideruiapi.config.ProviderUiProperties;
import gov.samhsa.c2s.provideruiapi.infrastructure.UmsClient;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ProfileResponse;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.provideruiapi.service.dto.JwtTokenKey;
import gov.samhsa.c2s.provideruiapi.service.dto.UserDto;
import gov.samhsa.c2s.provideruiapi.service.exception.DuplicateIdentifierValueException;
import gov.samhsa.c2s.provideruiapi.service.exception.UmsUserInterfaceException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UmsServiceImpl implements UmsService {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String ROLE_CODE = "patient";
    private static final int MIN_FIRST_NAME_LENGTH = 4;
    private static final int MIN_LAST_NAME_LENGTH = 4;
    private static final int MIN_PATIENT_ID_LENGTH = 4;
    private final JwtTokenExtractor jwtTokenExtractor;
    private final UmsClient umsClient;
    private final ModelMapper modelMapper;
    private final ProviderUiProperties providerUiProperties;

    @Autowired
    public UmsServiceImpl(JwtTokenExtractor jwtTokenExtractor, UmsClient umsClient, ModelMapper modelMapper, ProviderUiProperties providerUiProperties) {
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.umsClient = umsClient;
        this.modelMapper = modelMapper;
        this.providerUiProperties = providerUiProperties;
    }

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

        final String identifierDuplicateAssignErrorMsg = "this identifier system identifiers can only be assigned once";
        UmsUserDto tempUmsUserDto = new UmsUserDto();
        userDto.setCreatedBy(getLastUpdatedBy());
        userDto.setLastUpdatedBy(getLastUpdatedBy());
        try {
            tempUmsUserDto = umsClient.registerUser(modelMapper.map(userDto, UmsUserDto.class));
        } catch (FeignException feignErr) {
            String errorMessage = feignErr.getMessage();
            if (errorMessage.contains(identifierDuplicateAssignErrorMsg)) {
                log.info("This identifier value from the identifier system can only be assigned once.");
                throw new DuplicateIdentifierValueException("This identifier value from the identifier system can only be assigned once.");
            } else {
                log.error("Unexpected instance of FeignException has occurred", feignErr);
                throw new UmsUserInterfaceException("An unknown error occurred while attempting to communicate with PCM service");
            }
        }
        return modelMapper.map(tempUmsUserDto, UserDto.class);

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
        userDto.setLastUpdatedBy(getLastUpdatedBy());
        umsClient.updateUser(userId, modelMapper.map(userDto, UmsUserDto.class));
    }

    @Override
    public Object initiateUserActivation(Long userId, String xForwardedProto, String xForwardedHost, String xForwardedPort) {
        return umsClient.initiateUserActivation(userId, getLastUpdatedBy(), xForwardedProto, xForwardedHost, xForwardedPort);
    }

    @Override
    public Object getCurrentUserCreationInfo(Long userId) {
        return umsClient.getCurrentUserCreationInfo(userId);
    }

    @Override
    public void disableUser(Long userId) {
        umsClient.disableUser(userId, getLastUpdatedBy());

    }

    @Override
    public void enableUser(Long userId) {
        umsClient.enableUser(userId, getLastUpdatedBy());
    }

    @Override
    public ProfileResponse getProviderProfile() {
        //Get system supported Locales
        List<BaseUmsLookupDto> supportedLocales = umsClient.getLocales();

        String currentUsername = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_NAME);
        UmsUserDto umsUserDto = umsClient.getUserById(jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID));
        return ProfileResponse.builder()
                .userLocale(umsUserDto.getLocale())
                .supportedLocales(supportedLocales)
                .username(currentUsername)
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
        if (birthDate != null) {
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern(DATE_FORMAT);
            formatBirthday.append(birthDate.format(formatters));
        }

        PageableDto<UserDto> pageableUserDto = null;

        if (canSearchBasedOnConfiguration(firstName, lastName, genderCode, formatBirthday.toString(), mrn)) {
            PageableDto<UmsUserDto> pageableUmsUserDto = umsClient.searchUsersByDemographic(
                    firstName,
                    lastName,
                    genderCode,
                    formatBirthday.toString(),
                    mrn,
                    ROLE_CODE, page, size);
            List<UserDto> userDtos = pageableUmsUserDto.getContent().stream()
                    .map(umsUserDto -> modelMapper.map(umsUserDto, UserDto.class))
                    .collect(Collectors.toList());

            pageableUserDto = modelMapper.map(pageableUmsUserDto, pageableUserDtoType);
            pageableUserDto.setContent(userDtos);
        } else {
            pageableUserDto = new PageableDto<>();
        }

        return pageableUserDto;
    }

    private boolean canSearchBasedOnConfiguration(String firstName, String lastName, String gender, String dateOfBirth, String mrn) {
        ProviderUiProperties.PatientSearch patientSearch = providerUiProperties.getProviderPermissions().getPatientSearch();

        if (patientSearch.isFirstNameRequired() && (firstName == null || firstName.length() < MIN_FIRST_NAME_LENGTH)) {
            return false;
        } else if (patientSearch.isLastNameRequired() && (lastName == null || lastName.length() < MIN_LAST_NAME_LENGTH)) {
            return false;
        } else if (patientSearch.isGenderRequired() && (gender == null)) {
            return false;
        } else if (patientSearch.isDateOfBirthRequired() && (dateOfBirth == null)) {
            return false;
        } else if (patientSearch.isPatientIdRequired() && (mrn == null || mrn.length() < MIN_PATIENT_ID_LENGTH)) {
            return false;
        } else {
            return true;
        }
    }

    private String getLastUpdatedBy() {
        return jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
    }
}
