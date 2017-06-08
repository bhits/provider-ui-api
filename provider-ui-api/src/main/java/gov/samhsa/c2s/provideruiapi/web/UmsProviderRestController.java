package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.ProfileResponse;
import gov.samhsa.c2s.provideruiapi.service.UmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("ums/providers")
public class UmsProviderRestController {

    @Autowired
    private UmsService umsService;

    @GetMapping("/profile")
    public ProfileResponse getProviderProfile() {
        return umsService.getProviderProfile();
    }
}
