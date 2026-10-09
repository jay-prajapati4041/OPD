import { Routes } from '@angular/router'; // Import Routes type
import { PatientComponent } from './patient/patient.component'; // Import Patient component
import { AppointmentComponent } from './appointment/appointment.component'; // Import Appointment component
import { ConsultationComponent } from './consultation/consultation.component'; // Import Consultation component

export const routes: Routes = [ // Define app routes array
    { path: '', component: PatientComponent }, // Default route shows Patient registration
    { path: 'patients', component: PatientComponent }, // /patients also shows Patient component
    { path: 'appointments', component: AppointmentComponent }, // /appointments shows Appointment booking
    { path: 'consultations/:appointmentId', component: ConsultationComponent }, // /consultations/:id shows Consultation form
    { path: '**', redirectTo: '' } // Wildcard redirects unknown paths to home
]; // End routes array
