package com.pankarast.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class WorkingHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NonNull
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @NonNull
    private DayOfWeek dayOfWeek;

    @NonNull
    private LocalTime startTime;

    @NonNull
    private LocalTime endTime;
}
