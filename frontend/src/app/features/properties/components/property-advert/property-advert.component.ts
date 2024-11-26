import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormControl } from '@angular/forms';
import { PropertyService } from '../../services/property.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Property } from '../../models/property.model';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-property-advert',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule
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
  images: Array<{ imageId: number | null; filePath: string; file: File | null } | null> = new Array(10).fill(null);
  deletedImages: number[] = [];
  isEnglish: boolean = false;

  constructor(
    private fb: FormBuilder, 
    private propertyService: PropertyService, 
    private router: Router,
    private route: ActivatedRoute,
    private translate: TranslateService) {
    this.propertyForm = this.fb.group({
      propertyType: ['', Validators.required],
      title: ['', Validators.required],
      countryName: ['', Validators.required],
      locationName: ['', Validators.required],
      yearBuilt: ['', [Validators.min(1800), Validators.max(new Date().getFullYear())]],
      totalBuildingFloors: ['', [Validators.min(1)]],
      apartmentFloor: ['', [Validators.min(0)]],
      totalRooms: ['', [Validators.min(1)]],
      totalBedrooms: ['', [Validators.min(0)]],
      totalBathrooms: ['', [Validators.min(0)]],
      apartmentArea: ['', [Validators.required, Validators.min(1)]],
      price: ['', [Validators.required, Validators.min(0)]],
      currency: [this.isEnglish ? 'USD' : 'PLN'],
      images: [null, this.imagesValidator],
      description: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('propertyId');
    if (id) {
      this.propertyId = +id;
      this.isEditMode = true;
      this.loadPropertyData();
    }
    
    this.isEnglish = (this.translate.currentLang || 'en') === 'en';
  }

  loadPropertyData(): void {
    if (this.propertyId) {
      this.propertyService.getPropertyById(this.propertyId).subscribe({
        next: (property) => {
          this.propertyForm.patchValue(property);
          this.images = property.imageIds.map(imageId => ({
            imageId: imageId,
            filePath: this.propertyService.getImageUrl(imageId),
            file: null
          }));
          while (this.images.length < 10) {
            this.images.push(null);
          }
          this.updateImagesFormControl();
        },
        error: (err) => {
          console.error('Error loading property data', err);
        }
      });
    }
  }

  addImage(index: number) {
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'image/*';
    input.onchange = (event) => {
        const file = (event.target as HTMLInputElement).files?.[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                const imageUrl = URL.createObjectURL(file);
                this.images[index] = { imageId: null, filePath: imageUrl, file: file };
                this.updateImagesFormControl();
                this.propertyForm.get('images')?.markAsTouched();
            };
            reader.readAsDataURL(file);
        }
    };
    input.click();
  }

  removeImage(index: number, event: Event) {
    event.stopPropagation();
    const image = this.images[index];

    if (image && image.imageId && !image.file) {
      this.deletedImages.push(image.imageId);
    }

    this.images[index] = null;
    this.updateImagesFormControl();
  }

  private updateImagesFormControl() {
    this.propertyForm.get('images')?.setValue(this.images.filter(image => image !== null));
  }

  onSubmit() {
    if (this.propertyForm.valid) {
      if (this.isEditMode && this.propertyId) {
        this.propertyService.updateProperty(this.propertyId, this.propertyForm.value).subscribe({
          next: (property: Property) => {
            this.deletedImages.forEach(imageId => {
              this.propertyService.deleteImage(imageId).subscribe({
                error: (err) => console.error(`Error deleting image ${imageId}`, err)
              });
            });

            if (this.propertyForm.get('images')?.value) {
              this.uploadImages(property.id);
            }
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
    const images = this.propertyForm.get('images')?.value;

    if (images && images.length > 0) {
        const newImages = images.filter((image: { file: File | null }) => image && image.file instanceof File);

        newImages.forEach((image: { file: File | null }) => {
            if (image.file) { 
                formData.append('files', image.file);
            }
        });

        if (newImages.length > 0) {
          this.propertyService.uploadImage(propertyId, formData).subscribe({
              error: (err) => {
                  console.error('Error uploading images', err);
              }
          });
        }
    } 
  }

  imagesValidator(control: FormControl) {
    return control.value && control.value.length > 0 ? null : { required: true };
  }

  generateDescription() {
    this.errorMessage = null;
    this.isGenerating = true; 
    const language = this.isEnglish ? 'en' : 'pl'; 

    this.propertyService.generateDescription(this.propertyForm.value, language).subscribe({
        next: (response) => {
            const description = response.description;
            const sanitizedDescription = description.replace(/\*/g, '&#42;');
            this.propertyForm.patchValue({ description: sanitizedDescription }); 
            this.isGenerating = false; 
        },
        error: (err) => {
          this.errorMessage = this.translate.instant('ERROR_GENERATING_DESCRIPTION');
            console.error('Error generating description', err);
            this.isGenerating = false; 
        }
    });
  }

  get areRequiredFieldsFilled(): boolean {
    const requiredFields = ['propertyType', 'title', 'countryName', 'locationName', 'apartmentArea', 'price'];
    return requiredFields.every(field => this.propertyForm.get(field)?.valid);
  }

  get formTitle(): string {
    return this.isEditMode
      ? this.translate.instant('EDIT')
      : this.translate.instant('ADVERTISE');
  }

  get buttonLabel(): string {
    return this.isEditMode
      ? this.translate.instant('UPDATE_PROPERTY')
      : this.translate.instant('ADD_PROPERTY');
  }
}
