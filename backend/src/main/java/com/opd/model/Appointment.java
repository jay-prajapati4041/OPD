package com.opd.model; // Define model package

import jakarta.persistence.*; // Import all Jakarta JPA annotations
import java.time.LocalDate; // Import LocalDate for appointment date
import java.time.LocalDateTime; // Import LocalDateTime for createdAt timestamp
import java.time.LocalTime; // Import LocalTime for appointment time
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Import to prevent JSON serialization loops

@Entity // Mark this class as a JPA entity
@Table(name = "appointments") // Map to the 'appointments' table
public class Appointment { // Define Appointment class

    @Id // Mark as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id; // Appointment ID field

    @ManyToOne(fetch = FetchType.EAGER) // Many appointments can belong to one patient
    @JoinColumn(name = "patient_id", nullable = false) // Foreign key column
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Prevent Jackson serialization issues
    private Patient patient; // Patient associated with this appointment

    private String doctorName; // Doctor name field

    private LocalDate appointmentDate; // Date of the appointment

    private LocalTime appointmentTime; // Time of the appointment

    private String status; // Status: SCHEDULED or COMPLETED

    private LocalDateTime createdAt; // Timestamp when appointment was created

    @PrePersist // Run before entity is first saved
    public void prePersist() { // Pre-persist lifecycle callback
        this.createdAt = LocalDateTime.now(); // Set creation timestamp automatically
        if (this.status == null) { // If status not set
            this.status = "SCHEDULED"; // Default to SCHEDULED
        } // End if
    } // End prePersist

    public Long getId() { return id; } // Getter for id
    public void setId(Long id) { this.id = id; } // Setter for id

    public Patient getPatient() { return patient; } // Getter for patient
    public void setPatient(Patient patient) { this.patient = patient; } // Setter for patient

    public String getDoctorName() { return doctorName; } // Getter for doctorName
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; } // Setter for doctorName

    public LocalDate getAppointmentDate() { return appointmentDate; } // Getter for appointmentDate
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; } // Setter for appointmentDate

    public LocalTime getAppointmentTime() { return appointmentTime; } // Getter for appointmentTime
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; } // Setter for appointmentTime

    public String getStatus() { return status; } // Getter for status
    public void setStatus(String status) { this.status = status; } // Setter for status

    public LocalDateTime getCreatedAt() { return createdAt; } // Getter for createdAt
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } // Setter for createdAt

} // End Appointment class
