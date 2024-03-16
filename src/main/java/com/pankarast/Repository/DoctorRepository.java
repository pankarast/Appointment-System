package com.pankarast.Repository;

import com.pankarast.Domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Custom query methods if needed
    List<Doctor> findByArea(String area);
    List<Doctor> findBySpecialty(String specialty);
    Doctor findBySocialSecurityNumber(String socialSecurityNumber);
}

