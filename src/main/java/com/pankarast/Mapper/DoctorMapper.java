package com.pankarast.Mapper;

import com.pankarast.Domain.Doctor;
import com.pankarast.Dto.DoctorDTO;

public class DoctorMapper {

    public static DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setSocialSecurityNumber(doctor.getSocialSecurityNumber());
        dto.setName(doctor.getName());
        dto.setSpecialty(doctor.getSpecialty());
        dto.setContactDetails(doctor.getContactDetails());
        dto.setArea(doctor.getArea());
        return dto;
    }

    public static Doctor toEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setSocialSecurityNumber(dto.getSocialSecurityNumber());
        doctor.setName(dto.getName());
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setContactDetails(dto.getContactDetails());
        doctor.setArea(dto.getArea());

        return doctor;
    }
}
