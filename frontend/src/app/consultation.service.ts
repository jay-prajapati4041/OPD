import { Injectable } from '@angular/core'; // Import Injectable decorator
import { HttpClient } from '@angular/common/http'; // Import HttpClient
import { Observable } from 'rxjs'; // Import Observable

@Injectable({ // Injectable decorator
    providedIn: 'root' // Provide in root injector
}) // End decorator
export class ConsultationService { // Define ConsultationService class

    private apiUrl = 'http://localhost:8080/api/consultations'; // Backend base URL for consultations

    constructor(private http: HttpClient) { } // Inject HttpClient

    submitConsultation(appointmentId: number, dto: any): Observable<any> { // Method to submit a new consultation
        return this.http.post(`${this.apiUrl}/appointment/${appointmentId}`, dto); // POST to /api/consultations/appointment/{id}
    } // End submitConsultation

    getConsultationByAppointment(appointmentId: number): Observable<any> { // Get consultation for an appointment
        return this.http.get(`${this.apiUrl}/appointment/${appointmentId}`); // GET /api/consultations/appointment/{id}
    } // End getConsultationByAppointment

    getConsultationsByPatient(patientId: number): Observable<any[]> { // Get all consultations for a patient
        return this.http.get<any[]>(`${this.apiUrl}/patient/${patientId}`); // GET /api/consultations/patient/{id}
    } // End getConsultationsByPatient

} // End ConsultationService
