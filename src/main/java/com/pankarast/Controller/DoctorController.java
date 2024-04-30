package com.pankarast.Controller;


import com.pankarast.Dto.DoctorDTO;
import com.pankarast.Dto.WorkingHoursDTO;
import com.pankarast.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;


    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.findDoctorById(id));
    }



    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getDoctors(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String area) {

        List<DoctorDTO> doctors;

        if (specialty != null && area != null) {
            doctors = doctorService.findDoctorsByCriteria(specialty, area);
        } else {
            doctors = doctorService.findAllDoctors();
        }

        return ResponseEntity.ok(doctors);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO loggedInDoctor = doctorService.checkLogin(doctorDTO.getSocialSecurityNumber(), doctorDTO.getPassword());

        if (loggedInDoctor != null) {
            // Create a response map or a new DTO to include any additional login response information
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("user", loggedInDoctor);
            // Include a token if using JWT or similar authentication methods
            // response.put("token", "yourGeneratedTokenHere");

            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Invalid AMKA or password");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<DoctorDTO> registerDoctor(@RequestBody DoctorDTO doctorDTO) {
        if (doctorService.existsByAmka(doctorDTO.getSocialSecurityNumber())) {
            return ResponseEntity.badRequest().body(null); // Doctor already exists
        }
        DoctorDTO newDoctor = doctorService.createDoctorWithWorkingHours(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDoctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        if (doctorDTO == null) {
            return ResponseEntity.badRequest().body("Doctor data must not be null or empty");
        }
        System.out.println(doctorDTO);
        // Check each property of DoctorDTO
        StringBuilder validationErrors = new StringBuilder();
        if (doctorDTO.getName().isEmpty()) {
            validationErrors.append("Name cannot be null or empty. ");
        }
        if (doctorDTO.getSocialSecurityNumber().isEmpty()) {
            validationErrors.append("Social Security Number cannot be null or empty. ");
        }
        if (doctorDTO.getSpecialty().isEmpty()) {
            validationErrors.append("Specialty cannot be null or empty. ");
        }
        if (doctorDTO.getContactDetails().isEmpty()) {
            validationErrors.append("Contact Details cannot be null or empty. ");
        }
        if (doctorDTO.getArea().isEmpty()) {
            validationErrors.append("Area cannot be null or empty. ");
        }
        if (doctorDTO.getPassword() == null || doctorDTO.getPassword().isEmpty()) {
            validationErrors.append("Password cannot be null or empty. ");
        }
        if (doctorDTO.getFormattedAddress().isEmpty()) {
            validationErrors.append("Formatted Address cannot be null or empty. ");
        }
        if (doctorDTO.getLatitude() == null || doctorDTO.getLongitude() == null) {
            validationErrors.append("Latitude and Longitude cannot be null or empty. ");
        }
        if (doctorDTO.getWorkingHours() == null || doctorDTO.getWorkingHours().isEmpty()) {
            validationErrors.append("Working hours cannot be null or empty. ");
        }

        // If there are any validation errors, return a bad request with the errors
        if (validationErrors.length() > 0) {
            return ResponseEntity.badRequest().body(validationErrors.toString());
        }

        // Set the ID and attempt to update the doctor
        doctorDTO.setId(id);
        DoctorDTO updatedDoctor = doctorService.updateDoctor(doctorDTO);
        if (updatedDoctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDoctor);
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctorWithWorkingHours(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO createdDoctor = doctorService.createDoctorWithWorkingHours(doctorDTO);
        return ResponseEntity.ok(createdDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }
}

