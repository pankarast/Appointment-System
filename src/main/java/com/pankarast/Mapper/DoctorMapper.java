package com.pankarast.Mapper;

import com.pankarast.Domain.Doctor;
import com.pankarast.Dto.DoctorDTO;

public class DoctorMapper {

    public static DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialty(doctor.getSpecialty());
        dto.setContactDetails(doctor.getContactDetails());
        dto.setArea(doctor.getArea());
        // You can add more fields as needed
        return dto;
    }

    public static Doctor toEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setName(dto.getName());
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setContactDetails(dto.getContactDetails());
        doctor.setArea(dto.getArea());
        // You can add more fields as needed
        return doctor;
    }
}
