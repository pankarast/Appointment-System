package com.pankarast.Repository;

import com.pankarast.Domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Custom query methods if needed
}

