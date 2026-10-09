import { Component, OnInit } from '@angular/core'; // Import Component, OnInit
import { CommonModule } from '@angular/common'; // Import NgIf, NgFor
import { FormsModule } from '@angular/forms'; // Import NgModel for form binding
import { ActivatedRoute, Router } from '@angular/router'; // Import route and router
import { ConsultationService } from '../consultation.service'; // Import ConsultationService
import { AppointmentService, Appointment } from '../appointment.service'; // Import AppointmentService and Appointment

@Component({ // Component decorator
    selector: 'app-consultation', // Selector
    standalone: true, // Standalone component
    imports: [CommonModule, FormsModule], // Required modules
    templateUrl: './consultation.component.html', // Template file
    styleUrl: './consultation.component.css' // Styles file
}) // End decorator
export class ConsultationComponent implements OnInit { // Define ConsultationComponent

    appointmentId: number = 0; // Appointment ID from route param
    appointment: Appointment | null = null; // Full appointment details loaded from API
    existingConsultation: any = null; // Existing consultation if already submitted

    form = { // Consultation form model
        bodyTemperature: '', // Temperature in Celsius
        bloodPressure: '', // Blood pressure e.g. 120/80
        clinicalNotes: '' // Doctor's clinical notes
    }; // End form

    successMsg: string = ''; // Success message for user
    errorMsg: string = ''; // Error message for user
    loading: boolean = false; // Loading flag for submit button

    constructor( // Constructor
        private route: ActivatedRoute, // Inject ActivatedRoute to read URL params
        private router: Router, // Inject Router for back navigation
        private consultationService: ConsultationService, // Inject ConsultationService
        private appointmentService: AppointmentService // Inject AppointmentService
    ) { } // End constructor

    ngOnInit(): void { // On component init
        this.appointmentId = Number(this.route.snapshot.paramMap.get('appointmentId')); // Read ID from URL
        this.loadAppointment(); // Load appointment details
        this.loadExistingConsultation(); // Check if consultation already submitted
    } // End ngOnInit

    loadAppointment(): void { // Load appointment details from backend
        this.appointmentService.getAppointmentById(this.appointmentId).subscribe({ // Call API
            next: (data) => { // On success
                this.appointment = data; // Store appointment
            }, // End next
            error: () => { // On error
                this.errorMsg = 'Could not load appointment details.'; // Show error
            } // End error
        }); // End subscribe
    } // End loadAppointment

    loadExistingConsultation(): void { // Check if a consultation already exists
        this.consultationService.getConsultationByAppointment(this.appointmentId).subscribe({ // Call API
            next: (data) => { // On success (consultation found)
                this.existingConsultation = data; // Store existing consultation
            }, // End next
            error: () => { // 404 means no consultation yet — ignore error
                this.existingConsultation = null; // Ensure null if not found
            } // End error
        }); // End subscribe
    } // End loadExistingConsultation

    validateForm(): string | null { // Client-side validation; returns error or null
        if (!this.form.bodyTemperature) return 'Body temperature is required.'; // Required check
        const temp = parseFloat(this.form.bodyTemperature); // Parse as float
        if (isNaN(temp) || temp < 35.0 || temp > 42.0) { // Validate range
            return 'Body temperature must be between 35.0 and 42.0 °C.'; // Range error
        } // End if
        if (!this.form.bloodPressure.trim()) return 'Blood pressure is required.'; // Required check
        if (!this.form.clinicalNotes.trim()) return 'Clinical notes are required.'; // Required check
        return null; // No errors
    } // End validateForm

    submitConsultation(): void { // Submit consultation form
        const validationError = this.validateForm(); // Run validation
        if (validationError) { // If errors found
            this.errorMsg = validationError; // Show error
            this.successMsg = ''; // Clear success
            return; // Stop
        } // End if

        this.loading = true; // Set loading
        this.successMsg = ''; // Clear messages
        this.errorMsg = ''; // Clear messages

        const payload = { // Build request payload
            bodyTemperature: parseFloat(this.form.bodyTemperature), // Parse to number
            bloodPressure: this.form.bloodPressure, // As string
            clinicalNotes: this.form.clinicalNotes // As string
        }; // End payload

        this.consultationService.submitConsultation(this.appointmentId, payload).subscribe({ // Call API
            next: (data) => { // On success
                this.successMsg = 'Consultation submitted successfully! Appointment marked as COMPLETED.'; // Success message
                this.existingConsultation = data; // Store returned consultation
                this.loading = false; // Clear loading
                if (this.appointment) this.appointment.status = 'COMPLETED'; // Update local appointment status
            }, // End next
            error: (err) => { // On error
                this.errorMsg = err.error?.error || 'Failed to submit consultation. Please try again.'; // Backend error or fallback
                this.loading = false; // Clear loading
            } // End error
        }); // End subscribe
    } // End submitConsultation

    goBack(): void { // Navigate back to appointments page
        this.router.navigate(['/appointments']); // Go to appointments list
    } // End goBack

} // End ConsultationComponent
