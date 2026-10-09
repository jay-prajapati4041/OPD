package com.opd.model; // Define model package

import jakarta.persistence.Entity; // Import JPA Entity annotation
import jakarta.persistence.GeneratedValue; // Import JPA GeneratedValue annotation
import jakarta.persistence.GenerationType; // Import JPA GenerationType enum
import jakarta.persistence.Id; // Import JPA Id annotation
import jakarta.persistence.Table; // Import JPA Table annotation

@Entity // Mark this class as a JPA entity
@Table(name = "patients") // Map this entity to table 'patients'
public class Patient { // Define Patient class

    @Id // Mark this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment the ID
    private Long id; // Field for patient ID

    private String name; // Field for patient name
    private String gender; // Field for patient gender
    private Integer age; // Field for patient age
    private String phone; // Field for patient phone number

    public Patient() { // Default constructor for JPA
    } // End default constructor

    public Long getId() { // Getter for id
        return id; // Return id
    } // End getId

    public void setId(Long id) { // Setter for id
        this.id = id; // Set id
    } // End setId

    public String getName() { // Getter for name
        return name; // Return name
    } // End getName

    public void setName(String name) { // Setter for name
        this.name = name; // Set name
    } // End setName

    public String getGender() { // Getter for gender
        return gender; // Return gender
    } // End getGender

    public void setGender(String gender) { // Setter for gender
        this.gender = gender; // Set gender
    } // End setGender

    public Integer getAge() { // Getter for age
        return age; // Return age
    } // End getAge

    public void setAge(Integer age) { // Setter for age
        this.age = age; // Set age
    } // End setAge

    public String getPhone() { // Getter for phone
        return phone; // Return phone
    } // End getPhone

    public void setPhone(String phone) { // Setter for phone
        this.phone = phone; // Set phone
    } // End setPhone

} // End Patient class
