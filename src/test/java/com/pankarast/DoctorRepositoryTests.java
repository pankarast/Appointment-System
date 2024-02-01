package com.pankarast;

import com.pankarast.Domain.Doctor;
import com.pankarast.Repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DoctorRepositoryTests {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void testCreateAndFindDoctor() {
        Doctor doctor = new Doctor();
        doctor.setName("John Doe");
        doctor.setSpecialty("Cardiology");
        doctor.setContactDetails("123456789");
        doctor.setArea("New York");
        doctor = doctorRepository.save(doctor);

        Optional<Doctor> foundDoctor = doctorRepository.findById(doctor.getId());
        assertTrue(foundDoctor.isPresent());
        assertEquals(doctor.getName(), foundDoctor.get().getName());
    }
}

