package com.pankarast.Repository;

import com.pankarast.Domain.Doctor;
import com.pankarast.Domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Custom query methods if needed
}


