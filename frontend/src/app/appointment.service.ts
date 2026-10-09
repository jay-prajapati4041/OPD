import { Injectable } from '@angular/core'; // Import Injectable decorator
import { HttpClient } from '@angular/common/http'; // Import HttpClient for HTTP requests
import { Observable } from 'rxjs'; // Import Observable

export interface Appointment { // Define Appointment interface matching backend entity
    id?: number; // Optional appointment ID
    patient?: any; // Associated patient object
    doctorName: string; // Doctor name
    appointmentDate: string; // Date as ISO string (YYYY-MM-DD)
    appointmentTime: string; // Time as string (HH:mm)
    status?: string; // SCHEDULED or COMPLETED
    createdAt?: string; // Creation timestamp
} // End Appointment interface

@Injectable({ // Injectable decorator
    providedIn: 'root' // Provide in root injector
}) // End decorator
export class AppointmentService { // Define AppointmentService class

    private apiUrl = 'http://localhost:8080/api/appointments'; // Backend base URL for appointments

    constructor(private http: HttpClient) { } // Inject HttpClient

    bookAppointment(dto: any): Observable<any> { // Method to create a new appointment
        return this.http.post(this.apiUrl, dto); // POST to /api/appointments
    } // End bookAppointment

    getTodaysAppointments(): Observable<Appointment[]> { // Method to fetch today's appointments
        return this.http.get<Appointment[]>(`${this.apiUrl}/today`); // GET /api/appointments/today
    } // End getTodaysAppointments

    getAppointmentById(id: number): Observable<Appointment> { // Method to get single appointment
        return this.http.get<Appointment>(`${this.apiUrl}/${id}`); // GET /api/appointments/{id}
    } // End getAppointmentById

    getAppointmentsByPatient(patientId: number): Observable<Appointment[]> { // Method to fetch patient appointments
        return this.http.get<Appointment[]>(`${this.apiUrl}/patient/${patientId}`); // GET /api/appointments/patient/{id}
    } // End getAppointmentsByPatient

} // End AppointmentService
