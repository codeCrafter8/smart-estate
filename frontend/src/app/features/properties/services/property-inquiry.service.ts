import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PropertyInquiryService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  submitInquiry(propertyId: number, inquiryData: FormData): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/inquiries/${propertyId}`, inquiryData);
  }

  getInquiriesByProperty(propertyId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/inquiries/${propertyId}`);
  }
}
