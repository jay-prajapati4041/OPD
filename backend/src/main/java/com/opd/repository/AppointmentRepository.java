package com.opd.repository; // Define repository package

import com.opd.model.Appointment; // Import Appointment entity
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository
import org.springframework.stereotype.Repository; // Import Repository annotation
import java.time.LocalDate; // Import LocalDate
import java.time.LocalTime; // Import LocalTime
import java.util.List; // Import List

@Repository // Mark as Spring Data repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> { // Extend JpaRepository for Appointment with Long ID

    List<Appointment> findByAppointmentDate(LocalDate date); // Find all appointments on a given date

    boolean existsByDoctorNameAndAppointmentDateAndAppointmentTime(
            String doctorName, LocalDate appointmentDate, LocalTime appointmentTime); // Check for duplicate appointment slot

    List<Appointment> findByPatient_Id(Long patientId); // Find all appointments for a specific patient

} // End AppointmentRepository
