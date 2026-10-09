package com.opd.repository; // Define repository package

import com.opd.model.Patient; // Import Patient model
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository interface
import org.springframework.stereotype.Repository; // Import Repository annotation
import java.util.List; // Import List interface

@Repository // Mark interface as a Spring Data Repository
public interface PatientRepository extends JpaRepository<Patient, Long> { // Extend JpaRepository for Patient entity with Long ID
    
    List<Patient> findByNameContainingIgnoreCase(String name); // Custom query method to find patients by matching name ignoring case
    
    List<Patient> findByPhoneContaining(String phone); // Custom query method to find patients by matching phone number
    
    List<Patient> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone); // Custom query method to search by either name or phone

} // End interface


//   just call the main logic from here 

//  providing a crud operations 

// MERN equivalent: the database-access layer where you use Mongoose methods such as Patient.find(), Patient.findById(), and Patient.create().