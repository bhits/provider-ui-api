package gov.samhsa.c2s.provideruiapi.service;

import gov.samhsa.c2s.provideruiapi.service.dto.JwtTokenKey;

public interface JwtTokenExtractor {
    String getValueByKey(JwtTokenKey key);
}