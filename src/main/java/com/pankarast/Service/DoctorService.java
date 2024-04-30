package com.pankarast.Service;

import com.pankarast.Domain.Doctor;
import com.pankarast.Domain.WorkingHours;
import com.pankarast.Dto.DoctorDTO;
import com.pankarast.Dto.WorkingHoursDTO;
import com.pankarast.Mapper.DoctorMapper;
import com.pankarast.Repository.DoctorRepository;
import com.pankarast.Repository.WorkingHoursRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pankarast.Mapper.DoctorMapper.*;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public DoctorDTO checkLogin(String amka, String password) {
        Doctor doctor = doctorRepository.findBySocialSecurityNumber(amka);
        if (doctor != null && passwordEncoder.matches(password, doctor.getPassword())) {
            return DoctorMapper.toDTO(doctor); // Return patient data on successful login
        }
        return null; // Return null on login failure
    }

    public boolean existsByAmka(String amka) {
        return doctorRepository.findBySocialSecurityNumber(amka) != null;
    }


    public List<DoctorDTO> findDoctorsByCriteria(String specialty, String area) {
        List<Doctor> doctors = doctorRepository.findBySpecialtyAndArea(specialty, area);
        return doctors.stream().map(DoctorMapper::toDTO).collect(Collectors.toList());
    }
    public DoctorDTO registerDoctor(DoctorDTO doctorDTO) {
        doctorDTO.setPassword(passwordEncoder.encode(doctorDTO.getPassword())); // Hash the password
        Doctor doctor = DoctorMapper.toEntity(doctorDTO);
        doctor = doctorRepository.save(doctor);
        return DoctorMapper.toDTO(doctor);
    }

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = toEntity(doctorDTO); // Convert DTO to entity, ensuring no ID is set
        doctor = doctorRepository.save(doctor); // Save the new doctor
        return toDTO(doctor); // Convert back to DTO and return
    }

    @Transactional
    public DoctorDTO createDoctorWithWorkingHours(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        // Assume setters for basic doctor details based on doctorDTO's properties
        doctor.setSocialSecurityNumber(doctorDTO.getSocialSecurityNumber());
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor.setContactDetails(doctorDTO.getContactDetails());
        doctor.setArea(doctorDTO.getArea());
        doctor.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
        doctor.setFormattedAddress(doctorDTO.getFormattedAddress());
        doctor.setLongitude(doctorDTO.getLongitude());
        doctor.setLatitude(doctorDTO.getLatitude());

        Doctor savedDoctor = doctorRepository.save(doctor);

        // Process and save working hours
        if (doctorDTO.getWorkingHours() != null) {
            List<WorkingHours> workingHoursList = doctorDTO.getWorkingHours().stream().map(whDTO -> {
                WorkingHours wh = new WorkingHours();
                wh.setDoctor(savedDoctor);
                wh.setDayOfWeek(whDTO.getDayOfWeek());
                wh.setStartTime(whDTO.getStartTime());
                wh.setEndTime(whDTO.getEndTime());
                return wh;
            }).collect(Collectors.toList());

            workingHoursRepository.saveAll(workingHoursList);
            savedDoctor.setWorkingHours(new HashSet<>(workingHoursList));
        }

        return doctorDTO;
    }

    @Transactional
    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) {
        // Retrieve the existing doctor or throw if not found
        Doctor existingDoctor = doctorRepository.findById(doctorDTO.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Update the basic details of the doctor
        existingDoctor.setSocialSecurityNumber(doctorDTO.getSocialSecurityNumber());
        existingDoctor.setName(doctorDTO.getName());
        existingDoctor.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
        existingDoctor.setSpecialty(doctorDTO.getSpecialty());
        existingDoctor.setContactDetails(doctorDTO.getContactDetails());
        existingDoctor.setArea(doctorDTO.getArea());
        existingDoctor.setFormattedAddress(doctorDTO.getFormattedAddress());
        existingDoctor.setLongitude(doctorDTO.getLongitude());
        existingDoctor.setLatitude(doctorDTO.getLatitude());


        // Update password if provided
        if (doctorDTO.getPassword() != null && !doctorDTO.getPassword().isEmpty()) {
            existingDoctor.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
        }

        // Update or create working hours
        if (doctorDTO.getWorkingHours() != null) {
            updateWorkingHours(existingDoctor, doctorDTO.getWorkingHours());
        }

        // Save the updated doctor
        Doctor updatedDoctor = doctorRepository.save(existingDoctor);

        // Return the updated doctor details as DTO
        return toDTO(updatedDoctor);
    }

    public void updateWorkingHours(Doctor doctor, List<WorkingHoursDTO> workingHoursDTOs) {
        // Remove existing working hours linked to this doctor
        workingHoursRepository.deleteAll(doctor.getWorkingHours());
        Set <WorkingHours> workingHours = workingHoursDTOs.stream()
                .map(whDTO -> {
                    WorkingHours wh = new WorkingHours();
                    wh.setDoctor(doctor);
                    wh.setDayOfWeek(whDTO.getDayOfWeek());
                    wh.setStartTime(whDTO.getStartTime());
                    wh.setEndTime(whDTO.getEndTime());
                    return wh;
                })
                .collect(Collectors.toSet());
        // Update the working hours in the repository
        workingHoursRepository.saveAll(workingHours);

        // Link the new set of working hours to the doctor
        doctor.setWorkingHours(workingHours);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}

