package com.pankarast.Service;

import com.pankarast.Domain.Appointment;
import com.pankarast.Domain.Doctor;
import com.pankarast.Domain.WorkingHours;
import com.pankarast.Repository.AppointmentRepository;
import com.pankarast.Repository.DoctorRepository;
import com.pankarast.Repository.WorkingHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private WorkingHoursRepository workingHoursRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    // Inside ScheduleService.java
    public List<LocalTime> getAvailableSlots(Long doctorId, LocalDate date) {
        List<LocalTime> availableSlots = new ArrayList<>();
        final int slotDurationMinutes = 60; // Assuming 1-hour slots

        // 1.  Fetch all working hours for the doctor on the specified day
        List<WorkingHours> workingHoursList = workingHoursRepository.findByDoctorIdAndDayOfWeek(doctorId, date.getDayOfWeek());

        // 2. Fetch all appointments for the doctor on the specified date
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                doctorId,
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay());

        // Process working hours and appointments to calculate available slots

        for (WorkingHours wh : workingHoursList) {
            LocalTime startTime = wh.getStartTime();
            LocalTime endTime = wh.getEndTime();

            while (startTime.plusMinutes(slotDurationMinutes).isBefore(endTime) || startTime.plusMinutes(slotDurationMinutes).equals(endTime)) {
                final LocalTime slotStart = startTime;
                boolean isSlotOccupied = appointments.stream().anyMatch(appointment ->
                        !appointment.getAppointmentTime().toLocalTime().isBefore(slotStart) &&
                                appointment.getAppointmentTime().toLocalTime().isBefore(slotStart.plusMinutes(slotDurationMinutes)));

                if (!isSlotOccupied) {
                    availableSlots.add(startTime);
                }

                startTime = startTime.plusMinutes(slotDurationMinutes);
            }
        }

        return availableSlots;
    }

}
