package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ProfileResponse;
import gov.samhsa.c2s.provideruiapi.service.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UmsService {
    PageableDto<UserDto> getAllUsers(Integer page, Integer size);

    UserDto registerUser(UserDto userDto);

    List<UserDto> searchUsersByFirstNameAndORLastName(String term);

    UserDto getUser(Long userId);

    void updateUser(Long userId, UserDto userDto);

    Object initiateUserActivation(Long userId, String xForwardedProto, String xForwardedHost, int xForwardedPort);

    Object getCurrentUserCreationInfo(Long userId);

    void disableUser(Long userId);

    void enableUser(Long userId);

    ProfileResponse getProviderProfile();

    PageableDto<UserDto> searchUsersByDemographic( String firstName,
                                                   String lastName,
                                                   LocalDate birthDate,
                                                   String genderCode,
                                                   String mrn,
                                                   Integer page,
                                                   Integer size);

}