package com.opd.controller; // Define controller package

import com.opd.model.Patient; // Import Patient model
import com.opd.repository.PatientRepository; // Import PatientRepository
import org.springframework.beans.factory.annotation.Autowired; // Import Autowired annotation
import org.springframework.web.bind.annotation.*; // Import all web bind annotations for REST
import java.util.List; // Import List interface

@RestController // Mark this class as a REST Controller
@RequestMapping("/api/patients") // Set base URL path for this controller
@CrossOrigin(origins = "http://localhost:4200") // Allow CORS requests from local Angular frontend
public class PatientController { // Define controller class

    @Autowired // Inject the repository dependency
    private PatientRepository patientRepository; // Define repository field

    @PostMapping // Map HTTP POST requests to this method
    public Patient addPatient(@RequestBody Patient patient) { // Add a new patient mapping request body to Patient object
        return patientRepository.save(patient); // Save the patient and return it
    } // End addPatient method

    @GetMapping // Map HTTP GET requests to this method
    public List<Patient> getAllPatients() { // Method to get all patients
        return patientRepository.findAll(); // Return list of all patients from db
    } // End getAllPatients method

    @GetMapping("/search") // Map HTTP GET requests to /api/patients/search with query params
    public List<Patient> searchPatients(@RequestParam(required = false) String query) { // Method to search patients by query string
        if (query == null || query.trim().isEmpty()) { // Check if query is null or empty
            return patientRepository.findAll(); // Return all if no query
        } // End if block
        return patientRepository.findByNameContainingIgnoreCaseOrPhoneContaining(query, query); // Search DB for name or phone matching query
    } // End searchPatients method

    @GetMapping("/{id}") // GET /api/patients/{id} — fetch a single patient by ID
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) { // Accept ID as path variable
        return patientRepository.findById(id) // Look up patient
                .map(ResponseEntity::ok) // Return 200 with patient if found
                .orElse(ResponseEntity.notFound().build()); // Return 404 if not found
    } // End getPatientById

} // End PatientController class
