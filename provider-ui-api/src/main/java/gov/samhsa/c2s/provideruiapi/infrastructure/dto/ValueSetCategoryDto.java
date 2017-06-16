package gov.samhsa.c2s.provideruiapi.infrastructure.dto;

import lombok.Data;

@Data
public class ValueSetCategoryDto {

    /**
     * The code.
     */
    private String code;
    /**
     * The description.
     */
    private String description;
    /**
     * The name.
     */
    private String name;
    /**
     * The displayOrder.
     */
    private int displayOrder;
    /**
     * The isFederal.
     */
    private boolean isFederal;
    /**
     * The system.
     */
    private String system;
}