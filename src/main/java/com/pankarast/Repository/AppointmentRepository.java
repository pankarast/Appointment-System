package com.pankarast.Repository;

import com.pankarast.Domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByPatientIdAndAppointmentTimeBetween(Long patientId, LocalDateTime start, LocalDateTime end);
    // More custom query methods depending on requirements
}


