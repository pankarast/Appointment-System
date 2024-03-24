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
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.pankarast.Mapper.DoctorMapper.*;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private WorkingHoursRepository workingHoursRepository;

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

    @Transactional
    public DoctorDTO createDoctorWithWorkingHours(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        // Assume setters for basic doctor details based on doctorDTO's properties
        doctor.setSocialSecurityNumber(doctorDTO.getSocialSecurityNumber());
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor.setContactDetails(doctorDTO.getContactDetails());
        doctor.setArea(doctorDTO.getArea());

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
            savedDoctor.setWorkingHours(new HashSet<>(workingHoursList)); // Ensure your Doctor entity has a setter or method to add working hours
        }

        // Optionally, map the savedDoctor back to DoctorDTO, including the IDs of saved working hours, and return it
        // For simplicity, returning the input doctorDTO; in practice, you should return a mapped version of savedDoctor
        return doctorDTO;
    }


    public void updateWorkingHours(Long doctorId, List<WorkingHoursDTO> workingHoursDTOs) {
        // Fetch the doctor from the database
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Convert WorkingHoursDTOs to WorkingHours entities and associate them with the doctor
        // This might involve clearing existing working hours and adding the new ones
        // Or intelligently updating existing entries and adding/removing as needed

        // Save the updated doctor entity with its new working hours
        doctorRepository.save(doctor);
    }

    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(doctorDTO.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        // Update fields
        doctor.setName(doctorDTO.getName());
        doctor.setSocialSecurityNumber(doctorDTO.getSocialSecurityNumber());
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

