package com.pankarast.Dto;

import lombok.Data;

import java.util.List;

@Data
public class DoctorDTO {
    private Long id;
    private String name;
    private String socialSecurityNumber;
    private String specialty;
    private String contactDetails;
    private String area;
    private List<WorkingHoursDTO> workingHours;
    private List<AppointmentDTO> appointments;

    // Constructors, Getters, and Setters
}
