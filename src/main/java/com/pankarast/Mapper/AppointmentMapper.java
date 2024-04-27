package com.pankarast.Mapper;

import com.pankarast.Domain.Appointment;
import com.pankarast.Domain.Doctor;
import com.pankarast.Domain.Patient;
import com.pankarast.Dto.AppointmentDTO;

public class AppointmentMapper {

    public static AppointmentDTO toDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName(appointment.getDoctor().getName());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getName());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setReason(appointment.getReason());
        return dto;
    }

    public static Appointment toEntity(AppointmentDTO dto, Doctor doctor, Patient patient) {
        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setReason(dto.getReason());
        return appointment;
    }
}

