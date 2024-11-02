import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';
import { Property } from '../models/property.model';
import { environment } from '../../../../environments/environment';
import { PropertySearchCriteria } from '../models/property-search-criteria.model';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  searchProperties(criteria: PropertySearchCriteria): Observable<Property[]> {
    return this.http.post<Property[]>(`${this.apiUrl}/properties/search`, criteria).pipe(
      catchError((error) => {
        console.error('Error fetching properties:', error);
        return of([]); 
      })
    );
  }

  getImageUrl(imageId: number): string {
    return `${this.apiUrl}/images/${imageId}`;
  }

  addProperty(property: Property): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/properties`, property);
  }
  
  uploadImage(propertyId: number, formData: FormData) {
    return this.http.post(`${this.apiUrl}/images/upload/${propertyId}`, formData);
  }

  generateDescription(propertyDetails: Property): Observable<{ description: string }> {
    return this.http.post<{ description: string }>(`${this.apiUrl}/openai/generate-description`, propertyDetails);
  }

  getUserProperties(): Observable<Property[]> {
    return this.http.get<Property[]>(`${this.apiUrl}/properties/me`);
  }
}
