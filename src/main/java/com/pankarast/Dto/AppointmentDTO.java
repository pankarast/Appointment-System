package com.pankarast.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentTime;
    private String reason;
}
