package com.pankarast.Dto;

import lombok.Data;

@Data
public class PatientDTO {
    private Long id;
    private String socialSecurityNumber;
    private String name;
    private String contactDetails;
}
