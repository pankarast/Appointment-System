package com.pankarast.Domain;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String socialSecurityNumber;
    @NonNull
    private String name;
    @NonNull
    private String specialty;
    @NonNull
    private String contactDetails;
    @NonNull
    private String area;
    @NonNull
    private String password;

    // Address fields directly within Doctor class
    @NonNull
    private String formattedAddress;
    @NonNull
    private Double longitude;
    @NonNull
    private Double latitude;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WorkingHours> workingHours;
}
