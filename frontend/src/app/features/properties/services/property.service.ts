import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';
import { Property } from '../models/property';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  searchProperties(criteria: any): Observable<Property[]> {
    return this.http.post<Property[]>(`${this.apiUrl}/properties/search`, criteria).pipe(
      catchError((error) => {
        console.error('Error fetching properties:', error);
        return of([]); 
      })
    );
  }
}
