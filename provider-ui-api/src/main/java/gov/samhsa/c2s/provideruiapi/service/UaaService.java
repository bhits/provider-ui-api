package gov.samhsa.c2s.provideruiapi.service;


import gov.samhsa.c2s.provideruiapi.service.dto.LoginRequestDto;

public interface UaaService {
    Object login(LoginRequestDto requestDto);
}
