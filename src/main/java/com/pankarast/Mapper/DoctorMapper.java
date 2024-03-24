package com.pankarast.Mapper;

import com.pankarast.Domain.Doctor;
import com.pankarast.Dto.DoctorDTO;
import com.pankarast.Dto.WorkingHoursDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorMapper {

    public static DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setSocialSecurityNumber(doctor.getSocialSecurityNumber());
        dto.setName(doctor.getName());
        dto.setSpecialty(doctor.getSpecialty());
        dto.setContactDetails(doctor.getContactDetails());
        dto.setArea(doctor.getArea());

        if (doctor.getWorkingHours() != null && !doctor.getWorkingHours().isEmpty()) {
            List<WorkingHoursDTO> workingHoursDTOs = doctor.getWorkingHours().stream()
                    .map(wh -> WorkingHoursMapper.toDTO(wh))
                    .collect(Collectors.toList());
            dto.setWorkingHours(workingHoursDTOs);
        } else {
            dto.setWorkingHours(List.of()); // Ensure it's not null to avoid null checks on the client side
        }

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
