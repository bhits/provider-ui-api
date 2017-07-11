package gov.samhsa.c2s.provideruiapi.infrastructure;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.infrastructure.dto.UmsUserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("ums")
public interface UmsClient {
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    public static final String X_FORWARDED_HOST = "X-Forwarded-Host";
    public static final String X_FORWARDED_PORT = "X-Forwarded-Port";

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    PageableDto<UmsUserDto> getAllUsers(@RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "size", required = false) Integer size,
                                        @RequestParam(value = "role", required = false) String role);

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    UmsUserDto registerUser(@RequestBody UmsUserDto umsUserDto);

    @RequestMapping(value = "/users/search", method = RequestMethod.GET)
    List<UmsUserDto> searchUsersByFirstNameAndORLastName(@RequestParam("term") String term);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    UmsUserDto getUser(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    void updateUser(@PathVariable("userId") Long userId, @RequestBody UmsUserDto umsUserDto);

    @RequestMapping(value = "/users/{userId}/activation", method = RequestMethod.POST)
    Object initiateUserActivation(@PathVariable("userId") Long userId,
                                  @RequestHeader(X_FORWARDED_PROTO) String xForwardedProto,
                                  @RequestHeader(X_FORWARDED_HOST) String xForwardedHost,
                                  @RequestHeader(X_FORWARDED_PORT) int xForwardedPort);

    @RequestMapping(value = "/users/{userId}/activation", method = RequestMethod.GET)
    Object getCurrentUserCreationInfo(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/users/{userId}/disabled", method = RequestMethod.PUT)
    void disableUser(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/users/{userId}/enabled", method = RequestMethod.PUT)
    void enableUser(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/locales", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getLocales();

    @RequestMapping(value = "/users/search/patientDemographic", method = RequestMethod.GET)
    PageableDto<UmsUserDto> searchUsersByDemographic(@RequestParam(value = "firstName", required = false) String firstName,
                                                     @RequestParam(value = "lastName", required = false) String lastName,
                                                     @RequestParam(value = "genderCode", required = false) String genderCode,
                                                     @RequestParam(value = "birthDate", required = false) String birthDate,
                                                     @RequestParam(value = "mrn", required = false) String mrn,
                                                     @RequestParam(value = "roleCode", required = false) String roleCode,
                                                     @RequestParam(value = "page", required = false) Integer page,
                                                     @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/users/authId/{userAuthId}", method = RequestMethod.GET)
    UmsUserDto getUserById(@PathVariable("userAuthId") String userAuthId);
}