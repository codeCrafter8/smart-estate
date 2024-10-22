import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { PropertyService } from '../../services/property.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-property-advert',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
  ],
  templateUrl: './property-advert.component.html',
  styleUrl: './property-advert.component.scss'
})
export class PropertyAdvertComponent {
  propertyForm: FormGroup;

  constructor(private fb: FormBuilder, private propertyService: PropertyService, private router: Router) {
    this.propertyForm = this.fb.group({
      propertyType: ['', Validators.required],
      title: ['', Validators.required],
      countryName: ['', Validators.required],
      regionName: ['', Validators.required],
      yearBuilt: ['', [Validators.min(1800), Validators.max(new Date().getFullYear())]],
      totalBuildingFloors: ['', [Validators.min(1)]],
      apartmentFloor: ['', [Validators.min(0)]],
      totalRooms: ['', [Validators.min(1)]],
      totalBedrooms: ['', [Validators.min(0)]],
      totalBathrooms: ['', [Validators.min(0)]],
      apartmentArea: ['', [Validators.required, Validators.min(1)]],
      priceInUsd: ['', [Validators.required, Validators.min(0)]],
      images: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.propertyForm.valid) {
      this.propertyService.addProperty(this.propertyForm.value).subscribe({
        next: () => {
          this.router.navigate(['/properties']); 
        },
        error: (err) => {
          console.error('Error adding property', err);
        }
      });
    }
  }
}
