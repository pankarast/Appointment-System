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
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        doctorDTO.setId(id);
        return ResponseEntity.ok(doctorService.updateDoctor(doctorDTO));
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctorWithWorkingHours(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO createdDoctor = doctorService.createDoctorWithWorkingHours(doctorDTO);
        return ResponseEntity.ok(createdDoctor);
    }

    @PostMapping("/{id}/working-hours")
    public ResponseEntity<Void> updateWorkingHours(@PathVariable Long id, @RequestBody List<WorkingHoursDTO> workingHours) {
        doctorService.updateWorkingHours(id, workingHours);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }
}

