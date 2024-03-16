package com.pankarast.Dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long id;
    private String name;
    private String socialSecurityNumber;
    private String specialty;
    private String contactDetails;
    private String area;

    // Constructors, Getters, and Setters
}
