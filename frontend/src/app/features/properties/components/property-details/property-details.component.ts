import { Component } from '@angular/core';
import { Property } from '../../models/property.model';
import { ActivatedRoute } from '@angular/router';
import { PropertyService } from '../../services/property.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { PropertyInquiryService } from '../../services/property-inquiry.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

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
  messageClass: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private propertyService: PropertyService,
    private propertyInquiryService: PropertyInquiryService,
    private translate: TranslateService
  ) {
    this.contactForm = new FormGroup({
      phoneNumber: new FormControl('', [Validators.pattern(/^\+?[1-9]\d{1,14}$/)]),
      email: new FormControl('', [Validators.email])
    });
  }

  ngOnInit(): void {
    this.loadProperty();
  }

  loadProperty(): void {
    const propertyId = this.route.snapshot.paramMap.get('propertyId');
    if (propertyId) {
      this.propertyService.getPropertyById(+propertyId).subscribe(data => {
        this.property = data;
        this.updateImageDisplay();
      });
    }
  }

  getImageUrl(imageId: number): string {
    return this.propertyService.getImageUrl(imageId);
  }

  submitForm() {
    if(!this.isFormFilled()) {
      this.message = this.translate.instant('PHONE_OR_EMAIL_REQUIRED');
      this.messageClass = 'error';
      return;
    }

    const formData = this.contactForm.value;

    this.propertyInquiryService.submitInquiry(this.property.id, formData).subscribe({
      next: () => {
        this.message = this.translate.instant('INQUIRY_SUBMITTED');
        this.messageClass = 'success';
        this.contactForm.reset();
      },
      error: () => {
        this.message = this.translate.instant('SOMETHING_WENT_WRONG');
        this.messageClass = 'error';
      }
    });
  }

  prevImage() {
    if (this.canGoLeft) {
      this.currentIndex--;
      this.updateImageDisplay();
    }
  }

  nextImage() {
    if (this.canGoRight) {
      this.currentIndex++;
      this.updateImageDisplay();
    }
  }

  updateImageDisplay() {
    const galleryItem = document.querySelector('.gallery-item');
    const itemWidth = galleryItem ? (galleryItem as HTMLElement).offsetWidth : 0;
    this.translateXValue = -this.currentIndex * itemWidth;

    this.updateArrowVisibility();
  }

  updateArrowVisibility() {
    this.canGoLeft = this.currentIndex > 0;
    this.canGoRight = this.currentIndex < this.property.imageIds.length - 1;
  }

  isFormFilled(): boolean {
    return this.contactForm.get('phoneNumber')?.value || this.contactForm.get('email')?.value;
  }
}
