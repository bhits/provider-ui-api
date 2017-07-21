package gov.samhsa.c2s.provideruiapi.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateConsentException extends RuntimeException{
    public DuplicateConsentException(){
    }

    public DuplicateConsentException(String message){
        super(message);
    }
}
