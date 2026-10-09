import { Component, OnInit } from '@angular/core'; // Import Component, OnInit
import { CommonModule } from '@angular/common'; // Import NgIf, NgFor, DatePipe
import { FormsModule } from '@angular/forms'; // Import NgModel
import { Router } from '@angular/router'; // Import Router for navigation
import { AppointmentService, Appointment } from '../appointment.service'; // Import AppointmentService
import { PatientService, Patient } from '../patient.service'; // Import PatientService and Patient

@Component({ // Component decorator
    selector: 'app-appointment', // Selector
    standalone: true, // Standalone
    imports: [CommonModule, FormsModule], // Required modules
    templateUrl: './appointment.component.html', // Template file
    styleUrl: './appointment.component.css' // Styles file
}) // End decorator
export class AppointmentComponent implements OnInit { // Define AppointmentComponent

    patients: Patient[] = []; // List for patient dropdown
    todayAppointments: Appointment[] = []; // List for today's appointments table

    form = { // Booking form model
        patientId: '', // Selected patient ID
        doctorName: '', // Doctor name field
        appointmentDate: '', // Date field (YYYY-MM-DD)
        appointmentTime: '' // Time field (HH:mm)
    }; // End form

    successMsg: string = ''; // Success feedback message
    errorMsg: string = ''; // Error feedback message
    loading: boolean = false; // Loading flag
    loadingAppointments: boolean = false; // Loading flag for today's list

    todayString: string = new Date().toISOString().split('T')[0]; // Today's date as YYYY-MM-DD string for min attribute

    constructor( // Constructor
        private appointmentService: AppointmentService, // Inject AppointmentService
        private patientService: PatientService, // Inject PatientService
        private router: Router // Inject Router for navigation
    ) { } // End constructor

    ngOnInit(): void { // On init
        this.loadPatients(); // Load patient list for dropdown
        this.loadTodaysAppointments(); // Load today's appointments for display
    } // End ngOnInit

    loadPatients(): void { // Load all patients for the dropdown
        this.patientService.getAllPatients().subscribe((data) => { // Call API
            this.patients = data; // Store patients
        }); // End subscribe
    } // End loadPatients

    loadTodaysAppointments(): void { // Load today's appointment list
        this.loadingAppointments = true; // Set loading flag
        this.appointmentService.getTodaysAppointments().subscribe({ // Call API
            next: (data) => { // On success
                this.todayAppointments = data; // Store results
                this.loadingAppointments = false; // Clear loading
            }, // End next
            error: () => { // On error
                this.loadingAppointments = false; // Clear loading
            } // End error
        }); // End subscribe
    } // End loadTodaysAppointments

    validateForm(): string | null { // Client-side validation; returns error message or null
        if (!this.form.patientId) return 'Please select a patient.'; // Patient required
        if (!this.form.doctorName.trim()) return 'Doctor name is required.'; // Doctor required
        if (!this.form.appointmentDate) return 'Appointment date is required.'; // Date required
        if (!this.form.appointmentTime) return 'Appointment time is required.'; // Time required

        const selectedDate = new Date(this.form.appointmentDate + 'T' + this.form.appointmentTime); // Combine date and time
        if (selectedDate <= new Date()) return 'Appointment date and time must be in the future.'; // Past time check

        return null; // No errors
    } // End validateForm

    bookAppointment(): void { // Submit booking form
        const validationError = this.validateForm(); // Run client-side validation
        if (validationError) { // If any error found
            this.errorMsg = validationError; // Show error
            this.successMsg = ''; // Clear success
            return; // Stop here
        } // End if

        this.loading = true; // Enable loading state
        this.successMsg = ''; // Clear messages
        this.errorMsg = ''; // Clear messages

        this.appointmentService.bookAppointment(this.form).subscribe({ // Call booking API
            next: () => { // On success
                this.successMsg = 'Appointment booked successfully!'; // Success message
                this.form = { patientId: '', doctorName: '', appointmentDate: '', appointmentTime: '' }; // Reset form
                this.loadTodaysAppointments(); // Refresh today's list
                this.loading = false; // Clear loading
            }, // End next
            error: (err) => { // On error
                this.errorMsg = err.error?.error || 'Failed to book appointment. Please try again.'; // Show backend error or fallback
                this.loading = false; // Clear loading
            } // End error
        }); // End subscribe
    } // End bookAppointment

    startConsultation(appointment: Appointment): void { // Navigate to consultation form
        this.router.navigate(['/consultations', appointment.id]); // Route to consultation view
    } // End startConsultation

    isScheduled(appointment: Appointment): boolean { // Check if appointment is schedulable for consultation
        return appointment.status === 'SCHEDULED'; // Return true only if SCHEDULED
    } // End isScheduled

} // End AppointmentComponent
