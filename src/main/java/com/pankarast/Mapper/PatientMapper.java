package com.pankarast.Mapper;

import com.pankarast.Domain.Patient;
import com.pankarast.Dto.PatientDTO;

public class PatientMapper {

    public static PatientDTO toDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setSocialSecurityNumber(patient.getSocialSecurityNumber());
        dto.setName(patient.getName());
        dto.setContactDetails(patient.getContactDetails());
        // You can add more fields as needed
        return dto;
    }

    public static Patient toEntity(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setSocialSecurityNumber(dto.getSocialSecurityNumber());
        patient.setName(dto.getName());
        patient.setContactDetails(dto.getContactDetails());
        // You can add more fields as needed
        return patient;
    }
}
