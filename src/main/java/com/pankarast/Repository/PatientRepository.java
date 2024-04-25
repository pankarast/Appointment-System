package com.pankarast.Repository;

import com.pankarast.Domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findBySocialSecurityNumber(String socialSecurityNumber);
}



