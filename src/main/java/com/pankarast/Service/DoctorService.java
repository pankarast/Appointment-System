package com.pankarast.Service;

import com.pankarast.Domain.Doctor;
import com.pankarast.Dto.DoctorDTO;
import com.pankarast.Mapper.DoctorMapper;
import com.pankarast.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pankarast.Mapper.DoctorMapper.*;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<DoctorDTO> findAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        // Assuming a DoctorMapper similar to AppointmentMapper exists for converting entities to DTOs
        return doctors.stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }

    public DoctorDTO findDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return toDTO(doctor);
    }

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = toEntity(doctorDTO); // Convert DTO to entity, ensuring no ID is set
        doctor = doctorRepository.save(doctor); // Save the new doctor
        return toDTO(doctor); // Convert back to DTO and return
    }

    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(doctorDTO.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        // Update fields
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor.setContactDetails(doctorDTO.getContactDetails());
        doctor.setArea(doctorDTO.getArea());
        doctor = doctorRepository.save(doctor);
        return toDTO(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}

