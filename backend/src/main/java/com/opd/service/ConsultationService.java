package com.opd.service; // Define service package

import com.opd.model.Appointment; // Import Appointment entity
import com.opd.model.Consultation; // Import Consultation entity
import com.opd.repository.AppointmentRepository; // Import AppointmentRepository
import com.opd.repository.ConsultationRepository; // Import ConsultationRepository
import org.springframework.beans.factory.annotation.Autowired; // Import Autowired
import org.springframework.stereotype.Service; // Import Service annotation
import org.springframework.transaction.annotation.Transactional; // Import Transactional
import java.time.LocalDateTime; // Import LocalDateTime
import java.util.List; // Import List
import java.util.Optional; // Import Optional

@Service // Mark as a Spring service bean
public class ConsultationService { // Define ConsultationService class

    @Autowired // Inject AppointmentRepository
    private AppointmentRepository appointmentRepository; // Appointment data access

    @Autowired // Inject ConsultationRepository
    private ConsultationRepository consultationRepository; // Consultation data access

    @Transactional // Ensure both saves happen in one DB transaction
    public Consultation createConsultation(Long appointmentId, Double bodyTemperature,
                                           String bloodPressure, String clinicalNotes) { // Method to create and complete a consultation

        Appointment appointment = appointmentRepository.findById(appointmentId) // Find appointment by ID
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + appointmentId)); // Throw if missing

        if (!"SCHEDULED".equals(appointment.getStatus())) { // Check appointment is still scheduled
            throw new RuntimeException("Only SCHEDULED appointments can have a new consultation"); // Throw if already completed
        } // End if

        if (consultationRepository.existsByAppointment_Id(appointmentId)) { // Check if consultation already exists
            throw new RuntimeException("A consultation already exists for this appointment"); // Throw duplicate error
        } // End if

        if (bodyTemperature < 35.0 || bodyTemperature > 42.0) { // Validate temperature range
            throw new RuntimeException("Body temperature must be between 35.0 and 42.0 degrees Celsius"); // Throw if out of range
        } // End if

        if (bloodPressure == null || bloodPressure.trim().isEmpty()) { // Validate blood pressure is present
            throw new RuntimeException("Blood pressure is required"); // Throw if missing
        } // End if

        if (clinicalNotes == null || clinicalNotes.trim().isEmpty()) { // Validate clinical notes are present
            throw new RuntimeException("Clinical notes are required"); // Throw if missing
        } // End if

        Consultation consultation = new Consultation(); // Create new Consultation object
        consultation.setAppointment(appointment); // Associate with appointment
        consultation.setPatient(appointment.getPatient()); // Copy patient from appointment
        consultation.setDoctorName(appointment.getDoctorName()); // Copy doctor from appointment
        consultation.setConsultationDateTime(LocalDateTime.now()); // Set current date and time
        consultation.setBodyTemperature(bodyTemperature); // Set body temperature
        consultation.setBloodPressure(bloodPressure); // Set blood pressure
        consultation.setClinicalNotes(clinicalNotes); // Set clinical notes
        consultation.setStatus("COMPLETED"); // Mark consultation as completed

        appointment.setStatus("COMPLETED"); // Mark the associated appointment as completed
        appointmentRepository.save(appointment); // Save updated appointment status in DB

        return consultationRepository.save(consultation); // Save and return created consultation
    } // End createConsultation

    public Optional<Consultation> getConsultationByAppointment(Long appointmentId) { // Method to get consultation by appointment
        return consultationRepository.findByAppointment_Id(appointmentId); // Return optional consultation
    } // End getConsultationByAppointment

    public List<Consultation> getCompletedConsultationsByPatient(Long patientId) { // Method to get patient consultation history
        return consultationRepository // Use repository
                .findByPatient_IdAndStatusOrderByConsultationDateTimeDesc(patientId, "COMPLETED"); // Return completed consultations sorted newest first
    } // End getCompletedConsultationsByPatient

} // End ConsultationService
