package com.pankarast.Repository;

import com.pankarast.Domain.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {
    List<WorkingHours> findByDoctorIdAndDayOfWeek(Long doctorId, DayOfWeek dayOfWeek);
}
