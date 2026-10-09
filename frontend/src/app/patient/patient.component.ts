import { Component, OnInit } from '@angular/core'; // Import Component, OnInit
import { CommonModule } from '@angular/common'; // Import NgIf, NgFor directives
import { FormsModule } from '@angular/forms'; // Import NgModel directive
import { RouterLink } from '@angular/router'; // Import RouterLink for navigation
import { PatientService, Patient } from '../patient.service'; // Import PatientService and Patient interface
import { ConsultationService } from '../consultation.service'; // Import ConsultationService

@Component({ // Component decorator
    selector: 'app-patient', // Component selector
    standalone: true, // Standalone component
    imports: [CommonModule, FormsModule, RouterLink], // Required modules
    templateUrl: './patient.component.html', // Template file
    styleUrl: './patient.component.css' // Styles file
}) // End decorator
export class PatientComponent implements OnInit { // Define PatientComponent

    patients: Patient[] = []; // List of patients to display
    searchQuery: string = ''; // Current search query string

    newPatient: Patient = { // New patient form model
        name: '', // Empty name
        gender: '', // Empty gender
        age: 0, // Zero age
        phone: '' // Empty phone
    }; // End newPatient

    successMsg: string = ''; // Success message to show user
    errorMsg: string = ''; // Error message to show user
    loading: boolean = false; // Loading flag for form submission

    selectedPatientId: number | null = null; // Selected patient ID for consultation history
    consultationHistory: any[] = []; // Array to store consultation history

    constructor( // Constructor to inject services
        private patientService: PatientService, // Inject PatientService
        private consultationService: ConsultationService // Inject ConsultationService
    ) { } // End constructor

    ngOnInit(): void { // Lifecycle hook on component init
        this.loadPatients(); // Load all patients on start
    } // End ngOnInit

    loadPatients(): void { // Load full patient list from backend
        this.patientService.getAllPatients().subscribe((data) => { // Call API
            this.patients = data; // Store in array
        }); // End subscribe
    } // End loadPatients

    search(): void { // Search patients by name or phone
        this.patientService.searchPatients(this.searchQuery).subscribe((data) => { // Call search API
            this.patients = data; // Update displayed list
        }); // End subscribe
    } // End search

    resetSearch(): void { // Reset search and reload full list
        this.searchQuery = ''; // Clear query
        this.loadPatients(); // Reload all
    } // End resetSearch

    registerPatient(): void { // Submit the registration form
        this.loading = true; // Set loading true while submitting
        this.successMsg = ''; // Clear previous messages
        this.errorMsg = ''; // Clear errors

        this.patientService.addPatient(this.newPatient).subscribe({ // Call add API
            next: () => { // On success
                this.successMsg = 'Patient registered successfully!'; // Show success message
                this.loadPatients(); // Reload list
                this.newPatient = { name: '', gender: '', age: 0, phone: '' }; // Reset form
                this.loading = false; // Clear loading
            }, // End next
            error: () => { // On error
                this.errorMsg = 'Failed to register patient. Please try again.'; // Show error
                this.loading = false; // Clear loading
            } // End error
        }); // End subscribe
    } // End registerPatient

    viewHistory(patientId: number): void { // Load consultation history for a patient
        if (this.selectedPatientId === patientId) { // Toggle off if already selected
            this.selectedPatientId = null; // Deselect
            this.consultationHistory = []; // Clear history
            return; // Exit
        } // End if
        this.selectedPatientId = patientId; // Mark selected patient
        this.consultationService.getConsultationsByPatient(patientId).subscribe((data) => { // Fetch history
            this.consultationHistory = data; // Store history
        }); // End subscribe
    } // End viewHistory

} // End PatientComponent
