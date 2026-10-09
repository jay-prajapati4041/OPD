import { Injectable } from '@angular/core'; // Import Injectable
import { HttpClient } from '@angular/common/http'; // Import HttpClient
import { Observable } from 'rxjs'; // Import Observable

export interface Patient { // Define Patient interface
  id?: number; // Optional id
  name: string; // Patient name
  gender: string; // Patient gender
  age: number; // Patient age
  phone: string; // Patient phone
} // End Patient interface

@Injectable({ // Injectable decorator
  providedIn: 'root' // Root scope
}) // End decorator
export class PatientService { // Define PatientService class

  private apiUrl = 'http://localhost:8080/api/patients'; // Base API URL

  constructor(private http: HttpClient) { } // Inject HttpClient

  getAllPatients(): Observable<Patient[]> { // Method to get all patients
    return this.http.get<Patient[]>(this.apiUrl); // Return GET observable
  } // End getAllPatients

  searchPatients(query: string): Observable<Patient[]> { // Method to search patients
    return this.http.get<Patient[]>(`${this.apiUrl}/search?query=${query}`); // Return GET observable with query
  } // End searchPatients

  addPatient(patient: Patient): Observable<Patient> { // Method to add patient
    return this.http.post<Patient>(this.apiUrl, patient); // Return POST observable
  } // End addPatient
} // End PatientService class
