package gov.samhsa.c2s.provideruiapi.service.dto;

import lombok.Data;

@Data
public class ProviderDto {
    // TODO delete this class
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String providerType;
    private boolean deletable;
    private String address;
}
