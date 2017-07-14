package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.provideruiapi.service.UmsService;
import gov.samhsa.c2s.provideruiapi.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static gov.samhsa.c2s.provideruiapi.infrastructure.UmsClient.X_FORWARDED_HOST;
import static gov.samhsa.c2s.provideruiapi.infrastructure.UmsClient.X_FORWARDED_PORT;
import static gov.samhsa.c2s.provideruiapi.infrastructure.UmsClient.X_FORWARDED_PROTO;

@RestController
@RequestMapping("ums/users")
public class UmsRestController {

    private final UmsService umsService;

    @Autowired
    public UmsRestController(UmsService umsService) {
        this.umsService = umsService;
    }

    @GetMapping
    public PageableDto<UserDto> getUsers(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "size", required = false) Integer size) {
        return umsService.getAllUsers(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@Valid @RequestBody UserDto userDto) {
        return umsService.registerUser(userDto);
    }

    @GetMapping("/search")
    public List<UserDto> searchUsersByFirstNameAndORLastName(@RequestParam("term") String term) {
        return umsService.searchUsersByFirstNameAndORLastName(term);
    }


    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return umsService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public void editUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        umsService.updateUser(userId, userDto);
    }

    @PostMapping(value = "/{userId}/activation")
    @ResponseStatus(HttpStatus.CREATED)
    public Object initiateUserActivation(@PathVariable Long userId,
                                         @RequestHeader(X_FORWARDED_PROTO) String xForwardedProto,
                                         @RequestHeader(X_FORWARDED_HOST) String xForwardedHost,
                                         @RequestHeader(X_FORWARDED_PORT) int xForwardedPort) {
        return umsService.initiateUserActivation(userId, xForwardedProto, xForwardedHost, xForwardedPort);
    }

    @GetMapping(value = "/{userId}/activation")
    public Object getCurrentUserCreationInfo(@PathVariable Long userId) {
        return umsService.getCurrentUserCreationInfo(userId);
    }

    @PutMapping("/{userId}/disabled")
    public void disableUser(@PathVariable Long userId) {
        umsService.disableUser(userId);
    }

    @PutMapping("/{userId}/enabled")
    public void enableUser(@PathVariable Long userId) {
        umsService.enableUser(userId);
    }


    @GetMapping(value = "/search/patientDemographic")
    public PageableDto<UserDto> searchUsersByDemographic(@RequestParam(value = "firstName", required = false) String firstName,
                                                         @RequestParam(value = "lastName", required = false) String lastName,
                                                         @RequestParam(value = "genderCode", required = false) String genderCode,
                                                         @RequestParam(value = "birthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
                                                         @RequestParam(value = "mrn", required = false) String mrn,
                                                         @RequestParam(value = "page", required = false) Integer page,
                                                         @RequestParam(value = "size", required = false) Integer size) {
        return umsService.searchUsersByDemographic(firstName,lastName,birthDate,genderCode,mrn,page, size);
    }
}
