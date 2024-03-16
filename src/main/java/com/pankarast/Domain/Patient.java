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
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String socialSecurityNumber;
    @NonNull
    private String name;

    @NonNull
    private String password;

    @NonNull
    private String contactDetails;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;
}

