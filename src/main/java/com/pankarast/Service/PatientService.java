package com.pankarast.Service;

import com.pankarast.Domain.Patient;
import com.pankarast.Dto.PatientDTO;
import com.pankarast.Mapper.PatientMapper;
import com.pankarast.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pankarast.Mapper.PatientMapper.toDTO;
import static com.pankarast.Mapper.PatientMapper.toEntity;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkLogin(String amka, String password) {
        Patient patient = patientRepository.findBySocialSecurityNumber(amka);
        if (patient != null && passwordEncoder.matches(password, patient.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean existsByAmka(String amka) {
        return patientRepository.findBySocialSecurityNumber(amka) != null;
    }

    public PatientDTO registerPatient(PatientDTO patientDTO) {
        patientDTO.setPassword(passwordEncoder.encode(patientDTO.getPassword())); // Hash the password
        Patient patient = PatientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return PatientMapper.toDTO(patient);
    }


    public List<PatientDTO> findAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        // Assuming a PatientMapper similar to AppointmentMapper exists
        return patients.stream().map(PatientMapper::toDTO).collect(Collectors.toList());
    }

    public PatientDTO findPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return PatientMapper.toDTO(patient);
    }

    public PatientDTO updatePatient(PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(patientDTO.getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        // Update fields
        patient.setName(patientDTO.getName());
        patient.setSocialSecurityNumber(patientDTO.getSocialSecurityNumber());
        patient.setPassword(patientDTO.getPassword());
        patient.setContactDetails(patientDTO.getContactDetails());
        patient = patientRepository.save(patient);
        return PatientMapper.toDTO(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = toEntity(patientDTO); // Convert DTO to entity, ensuring no ID is set
        patient = patientRepository.save(patient); // Save the new doctor
        return toDTO(patient); // Convert back to DTO and return
    }

}


