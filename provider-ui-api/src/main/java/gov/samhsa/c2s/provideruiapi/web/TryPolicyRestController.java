package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.service.TryPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/try-policy")
public class TryPolicyRestController {

    @Autowired
    private TryPolicyService tryPolicyService;

    @GetMapping("/sampleDocuments")
    Object getSampleDocuments() {
        return tryPolicyService.getSampleDocuments();
    }
}