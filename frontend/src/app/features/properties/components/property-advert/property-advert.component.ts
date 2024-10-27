import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormControl } from '@angular/forms';
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
      images: [null, this.imagesValidator],
      description: ['', Validators.required],
    });
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.propertyForm.patchValue({
        images: input.files 
      });
    } 
    this.propertyForm.get('images')?.markAsTouched();
  }

  onSubmit() {
    if (this.propertyForm.valid) {
      this.propertyService.addProperty(this.propertyForm.value).subscribe({
        next: (propertyId: number) => {
          if (this.propertyForm.get('images')?.value) {
            this.uploadImages(propertyId);
          }
          this.router.navigate(['/properties']); 
        },
        error: (err) => {
          console.error('Error adding property', err);
        }
      });
    }
  }
  
  uploadImages(propertyId: number) {
    const formData = new FormData();
    const images: FileList = this.propertyForm.get('images')?.value;

    if (images) {
      Array.from(images).forEach(image => {
        formData.append('files', image);
      });
    }
  
    this.propertyService.uploadImage(propertyId, formData).subscribe({
      error: (err) => {
        console.error('Error uploading images', err);
      }
    });
  }

  imagesValidator(control: FormControl) {
    return control.value && control.value.length > 0 ? null : { required: true };
  }
}
