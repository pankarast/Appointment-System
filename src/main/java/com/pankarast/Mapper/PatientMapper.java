package com.pankarast.Mapper;

import com.pankarast.Domain.Patient;
import com.pankarast.Dto.PatientDTO;

public class PatientMapper {

    public static PatientDTO toDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setSocialSecurityNumber(patient.getSocialSecurityNumber());
        dto.setName(patient.getName());
        dto.setPassword(patient.getPassword());
        dto.setContactDetails(patient.getContactDetails());

        return dto;
    }

    public static Patient toEntity(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setSocialSecurityNumber(dto.getSocialSecurityNumber());
        patient.setName(dto.getName());
        patient.setPassword(dto.getPassword());
        patient.setContactDetails(dto.getContactDetails());

        return patient;
    }
}
