package com.opd.repository; // Define repository package

import com.opd.model.Consultation; // Import Consultation entity
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository
import org.springframework.stereotype.Repository; // Import Repository annotation
import java.util.List; // Import List
import java.util.Optional; // Import Optional

@Repository // Mark as Spring Data repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> { // Extend JpaRepository for Consultation

    boolean existsByAppointment_Id(Long appointmentId); // Check if a consultation already exists for an appointment

    Optional<Consultation> findByAppointment_Id(Long appointmentId); // Find consultation by appointment ID

    List<Consultation> findByPatient_IdAndStatusOrderByConsultationDateTimeDesc(
            Long patientId, String status); // Get completed consultations for a patient sorted by newest first

} // End ConsultationRepository
