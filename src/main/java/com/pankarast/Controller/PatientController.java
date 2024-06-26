package com.pankarast.Controller;

import com.pankarast.Dto.PatientDTO;
import com.pankarast.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow CORS from the React app origin
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PatientDTO patientDTO) {
        PatientDTO loggedInPatient = patientService.checkLogin(patientDTO.getSocialSecurityNumber(), patientDTO.getPassword());

        if (loggedInPatient != null) {
            // Create a response map or a new DTO to include any additional login response information
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("user", loggedInPatient);

            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Invalid AMKA or password");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<PatientDTO> registerPatient(@RequestBody PatientDTO patientDTO) {
        if (patientService.existsByAmka(patientDTO.getSocialSecurityNumber())) {
            return ResponseEntity.badRequest().body(null); // Patient already exists
        }
        PatientDTO newPatient = patientService.registerPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
    }


    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.findPatientById(id));
    }

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        patientDTO.setId(null); // Make sure ID is null for creation
        PatientDTO newPatient = patientService.createPatient(patientDTO); // Assuming a createDoctor method exists
        return ResponseEntity.ok(newPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        if (patientDTO == null) {
            return ResponseEntity.badRequest().body("Patient data must not be null");
        }

        // Check each property of PatientDTO
        StringBuilder validationErrors = new StringBuilder();
        if (patientDTO.getName().isEmpty()) {
            validationErrors.append("Name cannot be null. ");
        }
        if (patientDTO.getSocialSecurityNumber().isEmpty()) {
            validationErrors.append("Social Security Number cannot be null. ");
        }
        if (patientDTO.getPassword() == null || patientDTO.getPassword().isEmpty()) {
            validationErrors.append("Password cannot be null. ");
        }
        if (patientDTO.getContactDetails().isEmpty()) {
            validationErrors.append("Contact Details cannot be null. ");
        }


        // If there are any validation errors, return a bad request with the errors
        if (validationErrors.length() > 0) {
            return ResponseEntity.badRequest().body(validationErrors.toString());
        }

        // Set the ID and attempt to update the patient
        patientDTO.setId(id);
        PatientDTO updatedPatient = patientService.updatePatient(patientDTO);
        if (updatedPatient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPatient);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }
}
