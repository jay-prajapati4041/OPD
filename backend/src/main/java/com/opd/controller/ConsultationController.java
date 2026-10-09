package com.opd.controller; // Define controller package

import com.opd.model.Consultation; // Import Consultation model
import com.opd.service.ConsultationService; // Import ConsultationService
import org.springframework.beans.factory.annotation.Autowired; // Import Autowired
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity; // Import ResponseEntity
import org.springframework.web.bind.annotation.*; // Import REST annotations
import java.util.List; // Import List
import java.util.Map; // Import Map for request body

@RestController // Mark as REST controller
@RequestMapping("/api/consultations") // Base URL for consultation endpoints
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend CORS
public class ConsultationController { // Define ConsultationController class

    @Autowired // Inject ConsultationService
    private ConsultationService consultationService; // Service for consultation logic

    @PostMapping("/appointment/{appointmentId}") // POST /api/consultations/appointment/{id} — submit consultation
    public ResponseEntity<?> createConsultation(@PathVariable Long appointmentId,
                                                 @RequestBody Map<String, Object> body) { // Accept appointment ID and form data
        try { // Try to create consultation
            Double temperature = Double.parseDouble(body.get("bodyTemperature").toString()); // Parse temperature
            String bp = body.get("bloodPressure").toString(); // Get blood pressure string
            String notes = body.get("clinicalNotes").toString(); // Get clinical notes

            Consultation created = consultationService.createConsultation(appointmentId, temperature, bp, notes); // Call service
            return ResponseEntity.status(HttpStatus.CREATED).body(created); // Return 201 with created consultation
        } catch (RuntimeException e) { // Catch business-logic errors
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); // Return 400 with error message
        } // End catch
    } // End createConsultation

    @GetMapping("/appointment/{appointmentId}") // GET /api/consultations/appointment/{id} — get by appointment
    public ResponseEntity<?> getByAppointment(@PathVariable Long appointmentId) { // Accept appointment ID
        return consultationService.getConsultationByAppointment(appointmentId) // Lookup consultation
                .map(ResponseEntity::ok) // Return 200 with consultation if found
                .orElse(ResponseEntity.notFound().build()); // Return 404 if none
    } // End getByAppointment

    @GetMapping("/patient/{patientId}") // GET /api/consultations/patient/{id} — get history for patient
    public List<Consultation> getByPatient(@PathVariable Long patientId) { // Accept patient ID
        return consultationService.getCompletedConsultationsByPatient(patientId); // Return completed consultations
    } // End getByPatient

} // End ConsultationController
