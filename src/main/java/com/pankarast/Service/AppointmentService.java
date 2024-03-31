package com.pankarast.Service;

import com.pankarast.Domain.Appointment;
import com.pankarast.Domain.Doctor;
import com.pankarast.Domain.Patient;
import com.pankarast.Dto.AppointmentDTO;
import com.pankarast.Dto.PatientDTO;
import com.pankarast.Mapper.AppointmentMapper;
import com.pankarast.Repository.AppointmentRepository;
import com.pankarast.Repository.DoctorRepository;
import com.pankarast.Repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pankarast.Mapper.AppointmentMapper.toDTO;
import static com.pankarast.Mapper.AppointmentMapper.toEntity;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    public List<AppointmentDTO> findAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream().map(AppointmentMapper::toDTO).collect(Collectors.toList());
    }

    public AppointmentDTO findAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return AppointmentMapper.toDTO(appointment);
    }


    public List<AppointmentDTO> findAppointmentsByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        return appointments.stream().map(AppointmentMapper::toDTO).collect(Collectors.toList());
    }


    @Transactional
    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(appointmentDTO.getId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Patient patient = patientRepository.findById(appointmentDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        appointment.setReason(appointmentDTO.getReason());

        appointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDTO(appointment);
    }

    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        // Assuming you have a method to convert a DTO to an entity
        Appointment appointment = new Appointment();
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findById(appointmentDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        appointment.setReason(appointmentDTO.getReason());

        appointment = appointmentRepository.save(appointment); // Save the new appointment
        return toDTO(appointment); // Convert back to DTO and return
    }


    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}


