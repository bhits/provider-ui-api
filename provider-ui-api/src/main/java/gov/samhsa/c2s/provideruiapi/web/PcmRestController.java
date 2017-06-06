package gov.samhsa.c2s.provideruiapi.web;

import gov.samhsa.c2s.provideruiapi.infrastructure.dto.IdentifiersDto;
import gov.samhsa.c2s.provideruiapi.service.PcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pcm")
public class PcmRestController {

    @Autowired
    private PcmService pcmService;

    @GetMapping("/patients/{mrn}/providers")
    public List<Object> getProviders(@PathVariable String mrn) {
        return pcmService.getProviders(mrn);
    }

    @PostMapping("/patients/{mrn}/providers")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProviders(@PathVariable String mrn,
                              @Valid @RequestBody IdentifiersDto providerIdentifiersDto) {
        pcmService.saveProviders(mrn, providerIdentifiersDto);
    }

    @DeleteMapping("/patients/{mrn}/providers/{providerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvider(@PathVariable String mrn,
                               @PathVariable Long providerId) {
        pcmService.deleteProvider(mrn, providerId);
    }

    @GetMapping("/purposes")
    public List<Object> getPurposes() {
        return pcmService.getPurposes();
    }
}