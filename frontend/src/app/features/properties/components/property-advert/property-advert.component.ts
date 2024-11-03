import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormControl } from '@angular/forms';
import { PropertyService } from '../../services/property.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Property } from '../../models/property.model';

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
export class PropertyAdvertComponent implements OnInit {
  propertyForm: FormGroup;
  isGenerating = false;
  errorMessage: string | null = null;
  isEditMode = false;
  propertyId: number | null = null;

  constructor(
    private fb: FormBuilder, 
    private propertyService: PropertyService, 
    private router: Router,
    private route: ActivatedRoute) {
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

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('propertyId');
      if (id) {
        this.propertyId = +id;
        this.isEditMode = true;
        this.loadPropertyData();
      }
    });
  }

  loadPropertyData(): void {
    if (this.propertyId) {
      this.propertyService.getPropertyById(this.propertyId).subscribe({
        next: (property) => {
          this.propertyForm.patchValue(property);
        },
        error: (err) => {
          console.error('Error loading property data', err);
        }
      });
    }
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
      if (this.isEditMode && this.propertyId) {
        this.propertyService.updateProperty(this.propertyId, this.propertyForm.value).subscribe({
          next: () => {
            this.router.navigate(['/my-adverts']);
          },
          error: (err) => {
            console.error('Error updating property', err);
          }
        });
      } else {
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

  generateDescription() {
    this.isGenerating = true; 
    this.propertyService.generateDescription(this.propertyForm.value).subscribe({
        next: (response) => {
            const description = response.description;
            const sanitizedDescription = description.replace(/\*/g, '&#42;');
            this.propertyForm.patchValue({ description: sanitizedDescription }); 
            this.isGenerating = false; 
        },
        error: (err) => {
            this.errorMessage = 'Failed to generate description. Please try again.';
            console.error('Error generating description', err);
            this.isGenerating = false; 
        }
    });
  }

  get areRequiredFieldsFilled(): boolean {
    const requiredFields = ['propertyType', 'title', 'countryName', 'regionName', 'apartmentArea', 'priceInUsd'];
    return requiredFields.every(field => this.propertyForm.get(field)?.valid);
  } 
}
