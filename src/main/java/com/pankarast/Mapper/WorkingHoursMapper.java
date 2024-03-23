package com.pankarast.Mapper;

import com.pankarast.Domain.Doctor;
import com.pankarast.Domain.WorkingHours;
import com.pankarast.Dto.WorkingHoursDTO;

public class WorkingHoursMapper {

    public static WorkingHoursDTO toDTO(WorkingHours workingHours) {
        WorkingHoursDTO dto = new WorkingHoursDTO();
        dto.setId(workingHours.getId());
        dto.setDoctorId(workingHours.getDoctor().getId());
        dto.setDayOfWeek(workingHours.getDayOfWeek());
        dto.setStartTime(workingHours.getStartTime());
        dto.setEndTime(workingHours.getEndTime());
        return dto;
    }

    public static WorkingHours toEntity(WorkingHoursDTO dto, Doctor doctor) {
        WorkingHours workingHours = new WorkingHours();
        workingHours.setId(dto.getId());
        workingHours.setDoctor(doctor);
        workingHours.setDayOfWeek(dto.getDayOfWeek());
        workingHours.setStartTime(dto.getStartTime());
        workingHours.setEndTime(dto.getEndTime());
        return workingHours;
    }
}
