import { Component } from '@angular/core';
import { Property } from '../../models/property.model';
import { ActivatedRoute } from '@angular/router';
import { PropertyService } from '../../services/property.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { PropertyInquiryService } from '../../services/property-inquiry.service';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-property-details',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TranslateModule],
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.scss']
})
export class PropertyDetailsComponent {
  contactForm: FormGroup;
  property!: Property;
  translateXValue = 0;
  currentIndex = 0;
  canGoLeft = false;
  canGoRight = false;
  message: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private propertyService: PropertyService,
    private propertyInquiryService: PropertyInquiryService
  ) {
    this.contactForm = new FormGroup({
      phoneNumber: new FormControl('', [Validators.pattern(/^\+?[1-9]\d{1,14}$/)]),
      email: new FormControl('', [Validators.email])
    });
  }

  ngOnInit(): void {
    const propertyId = this.route.snapshot.paramMap.get('propertyId');
    if (propertyId) {
      this.propertyService.getPropertyById(+propertyId).subscribe(data => {
        this.property = data;
        this.updateImagePosition();  
        this.updateArrowVisibility();  
      });
    }
  }

  getImageUrl(imageId: number): string {
    return this.propertyService.getImageUrl(imageId);
  }

  submitForm() {
    if(!this.contactForm.valid) {
      this.message = 'Your inquiry has been successfully submitted!';
      return;
    }

    const formData = this.contactForm.value;

    this.propertyInquiryService.submitInquiry(this.property.id, formData).subscribe({
      next: () => {
        this.message = 'Your inquiry has been successfully submitted!';
        this.contactForm.reset(); 
      },
      error: (error) => {
        console.error('Error submitting inquiry:', error);
        this.message = 'Something went wrong. Please try again later.';
      }
    }); 
  }

  prevImage() {
    if (this.canGoLeft) {
      this.currentIndex--;
      this.updateImagePosition();
      this.updateArrowVisibility();
    }
  }

  nextImage() {
    if (this.canGoRight) {
      this.currentIndex++;
      this.updateImagePosition();
      this.updateArrowVisibility();
    }
  }

  updateImagePosition() {
    const galleryItem = document.querySelector('.gallery-item');
    const itemWidth = galleryItem ? (galleryItem as HTMLElement).offsetWidth : 0; 
    this.translateXValue = -this.currentIndex * itemWidth; 
  }

  updateArrowVisibility() {
    this.canGoLeft = this.currentIndex > 0;
    this.canGoRight = this.currentIndex < this.property.imageIds.length - 1;
  }

  isFormFilled(): boolean {
    return this.contactForm.get('phoneNumber')?.value || this.contactForm.get('email')?.value;
  }
}
