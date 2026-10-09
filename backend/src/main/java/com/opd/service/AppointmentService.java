package com.opd.service; // Define service package

import com.opd.model.Appointment; // Import Appointment entity
import com.opd.model.Patient; // Import Patient entity
import com.opd.repository.AppointmentRepository; // Import AppointmentRepository
import com.opd.repository.PatientRepository; // Import PatientRepository
import org.springframework.beans.factory.annotation.Autowired; // Import Autowired
import org.springframework.stereotype.Service; // Import Service annotation
import java.time.LocalDate; // Import LocalDate
import java.time.LocalTime; // Import LocalTime
import java.util.List; // Import List
import java.util.Optional; // Import Optional

@Service // Mark as a Spring service bean
public class AppointmentService { // Define service class

    @Autowired // Inject AppointmentRepository dependency
    private AppointmentRepository appointmentRepository; // Repository for appointments

    @Autowired // Inject PatientRepository dependency
    private PatientRepository patientRepository; // Repository for patients

    public Appointment createAppointment(Long patientId, String doctorName,
                                         LocalDate appointmentDate, LocalTime appointmentTime) { // Method to create a new appointment

        if (appointmentDate.isBefore(LocalDate.now())) { // Check if date is in the past
            throw new RuntimeException("Appointment date cannot be in the past"); // Throw error for past date
        } // End if

        if (appointmentDate.isEqual(LocalDate.now()) && appointmentTime.isBefore(LocalTime.now())) { // Check if today's time has passed
            throw new RuntimeException("Appointment time cannot be in the past"); // Throw error for past time
        } // End if

        boolean duplicate = appointmentRepository // Check for duplicate slot
                .existsByDoctorNameAndAppointmentDateAndAppointmentTime(
                        doctorName, appointmentDate, appointmentTime); // Use repository duplicate check

        if (duplicate) { // If duplicate found
            throw new RuntimeException("Doctor already has an appointment at this date and time"); // Throw duplicate error
        } // End if

        Patient patient = patientRepository.findById(patientId) // Find patient by ID
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId)); // Throw if not found

        Appointment appointment = new Appointment(); // Create new Appointment object
        appointment.setPatient(patient); // Set patient
        appointment.setDoctorName(doctorName); // Set doctor name
        appointment.setAppointmentDate(appointmentDate); // Set date
        appointment.setAppointmentTime(appointmentTime); // Set time
        appointment.setStatus("SCHEDULED"); // Set initial status as SCHEDULED

        return appointmentRepository.save(appointment); // Save and return appointment
    } // End createAppointment

    public List<Appointment> getTodaysAppointments() { // Method to get today's appointments
        return appointmentRepository.findByAppointmentDate(LocalDate.now()); // Return appointments for today
    } // End getTodaysAppointments

    public Optional<Appointment> getAppointmentById(Long id) { // Method to find appointment by ID
        return appointmentRepository.findById(id); // Return optional appointment
    } // End getAppointmentById

    public List<Appointment> getAppointmentsByPatient(Long patientId) { // Method to get patient appointments
        return appointmentRepository.findByPatient_Id(patientId); // Return list of patient appointments
    } // End getAppointmentsByPatient

} // End AppointmentService
