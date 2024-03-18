package com.pankarast.Controller;

import com.pankarast.Dto.DoctorDTO;
import com.pankarast.Dto.PatientDTO;
import com.pankarast.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow CORS from the React app origin
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PatientDTO patientDTO) {
        boolean isAuthenticated = patientService.checkLogin(patientDTO.getSocialSecurityNumber(), patientDTO.getPassword());

        if (isAuthenticated) {
            // Consider creating a successful login response, possibly including JWT or another form of token.
            return ResponseEntity.ok().build();
        } else {
            // Consider providing a more specific error message or handling for failed authentication attempts.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        patientDTO.setId(id);
        return ResponseEntity.ok(patientService.updatePatient(patientDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }
}
