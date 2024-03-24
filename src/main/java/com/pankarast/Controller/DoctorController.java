package com.pankarast.Controller;


import com.pankarast.Dto.DoctorDTO;
import com.pankarast.Dto.WorkingHoursDTO;
import com.pankarast.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.findDoctorById(id));
    }

//    @PostMapping
//    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
//        // Ensure the DTO doesn't carry an ID for creation
//        doctorDTO.setId(null); // Make sure ID is null for creation
//        DoctorDTO newDoctor = doctorService.createDoctor(doctorDTO); // Assuming a createDoctor method exists
//        return ResponseEntity.status(HttpStatus.CREATED).body(newDoctor);
//    }


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

