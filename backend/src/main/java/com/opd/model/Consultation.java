package com.opd.model; // Define model package

import jakarta.persistence.*; // Import all Jakarta JPA annotations
import java.time.LocalDateTime; // Import LocalDateTime for consultation timestamp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Import to prevent JSON loops

@Entity // Mark this class as a JPA entity
@Table(name = "consultations") // Map to 'consultations' table
public class Consultation { // Define Consultation class

    @Id // Mark as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id; // Consultation ID field

    @OneToOne(fetch = FetchType.EAGER) // One consultation per appointment
    @JoinColumn(name = "appointment_id", nullable = false, unique = true) // Unique FK to appointments
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Prevent Jackson serialization issues
    private Appointment appointment; // Associated appointment

    @ManyToOne(fetch = FetchType.EAGER) // Many consultations can belong to one patient (historically)
    @JoinColumn(name = "patient_id", nullable = false) // FK to patients table
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Prevent Jackson serialization issues
    private Patient patient; // Associated patient

    private String doctorName; // Doctor who conducted the consultation

    private LocalDateTime consultationDateTime; // Date and time of the consultation

    private Double bodyTemperature; // Body temperature in Celsius

    private String bloodPressure; // Blood pressure reading e.g. "120/80"

    @Column(length = 2000) // Allow longer text for clinical notes
    private String clinicalNotes; // Doctor's clinical observations and notes

    private String status; // Status: always COMPLETED after submission

    public Long getId() { return id; } // Getter for id
    public void setId(Long id) { this.id = id; } // Setter for id

    public Appointment getAppointment() { return appointment; } // Getter for appointment
    public void setAppointment(Appointment appointment) { this.appointment = appointment; } // Setter for appointment

    public Patient getPatient() { return patient; } // Getter for patient
    public void setPatient(Patient patient) { this.patient = patient; } // Setter for patient

    public String getDoctorName() { return doctorName; } // Getter for doctorName
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; } // Setter for doctorName

    public LocalDateTime getConsultationDateTime() { return consultationDateTime; } // Getter for consultationDateTime
    public void setConsultationDateTime(LocalDateTime consultationDateTime) { this.consultationDateTime = consultationDateTime; } // Setter for consultationDateTime

    public Double getBodyTemperature() { return bodyTemperature; } // Getter for bodyTemperature
    public void setBodyTemperature(Double bodyTemperature) { this.bodyTemperature = bodyTemperature; } // Setter for bodyTemperature

    public String getBloodPressure() { return bloodPressure; } // Getter for bloodPressure
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; } // Setter for bloodPressure

    public String getClinicalNotes() { return clinicalNotes; } // Getter for clinicalNotes
    public void setClinicalNotes(String clinicalNotes) { this.clinicalNotes = clinicalNotes; } // Setter for clinicalNotes

    public String getStatus() { return status; } // Getter for status
    public void setStatus(String status) { this.status = status; } // Setter for status

} // End Consultation class
