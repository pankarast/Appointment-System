package com.pankarast.Controller;

import com.pankarast.Service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/{doctorId}/available-slots")
    public ResponseEntity<List<LocalTime>> getAvailableSlots(@PathVariable Long doctorId,
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<LocalTime> availableSlots = scheduleService.getAvailableSlots(doctorId, date);
        return ResponseEntity.ok(availableSlots);
    }
}
