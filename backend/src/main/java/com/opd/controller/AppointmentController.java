package com.opd.controller; // Define controller package

import com.opd.model.Appointment; // Import Appointment model
import com.opd.service.AppointmentService; // Import AppointmentService
import org.springframework.beans.factory.annotation.Autowired; // Import Autowired
import org.springframework.http.HttpStatus; // Import HttpStatus for response codes
import org.springframework.http.ResponseEntity; // Import ResponseEntity
import org.springframework.web.bind.annotation.*; // Import all web REST annotations
import java.time.LocalDate; // Import LocalDate
import java.time.LocalTime; // Import LocalTime
import java.util.List; // Import List
import java.util.Map; // Import Map for request body parsing

@RestController // Mark as REST controller
@RequestMapping("/api/appointments") // Base URL for appointment endpoints
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend CORS
public class AppointmentController { // Define AppointmentController class

    @Autowired // Inject AppointmentService
    private AppointmentService appointmentService; // Service for appointment logic

    @PostMapping // POST /api/appointments — create a new appointment
    public ResponseEntity<?> createAppointment(@RequestBody Map<String, Object> body) { // Accept raw map for flexibility
        try { // Try to create appointment
            Long patientId = Long.parseLong(body.get("patientId").toString()); // Parse patient ID from body
            String doctorName = body.get("doctorName").toString(); // Get doctor name from body
            LocalDate date = LocalDate.parse(body.get("appointmentDate").toString()); // Parse date string to LocalDate
            LocalTime time = LocalTime.parse(body.get("appointmentTime").toString()); // Parse time string to LocalTime

            Appointment created = appointmentService.createAppointment(patientId, doctorName, date, time); // Call service to create
            return ResponseEntity.status(HttpStatus.CREATED).body(created); // Return 201 Created with appointment
        } catch (RuntimeException e) { // Catch business-logic errors
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); // Return 400 with error message
        } // End catch
    } // End createAppointment

    @GetMapping("/today") // GET /api/appointments/today — list today's appointments
    public List<Appointment> getTodaysAppointments() { // Method for today's list
        return appointmentService.getTodaysAppointments(); // Return today's appointments from service
    } // End getTodaysAppointments

    @GetMapping("/{id}") // GET /api/appointments/{id} — get appointment by ID
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id) { // Accept path variable ID
        return appointmentService.getAppointmentById(id) // Find by ID
                .map(ResponseEntity::ok) // Return 200 with appointment if found
                .orElse(ResponseEntity.notFound().build()); // Return 404 if not found
    } // End getAppointmentById

    @GetMapping("/patient/{patientId}") // GET /api/appointments/patient/{patientId} — get by patient
    public List<Appointment> getByPatient(@PathVariable Long patientId) { // Accept patient ID path variable
        return appointmentService.getAppointmentsByPatient(patientId); // Return patient's appointments
    } // End getByPatient

} // End AppointmentController
