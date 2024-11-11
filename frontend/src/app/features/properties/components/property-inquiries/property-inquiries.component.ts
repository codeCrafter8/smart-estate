import { Component, OnInit } from '@angular/core';
import { PropertyInquiryService } from '../../services/property-inquiry.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-property-inquiries',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './property-inquiries.component.html',
  styleUrl: './property-inquiries.component.scss'
})
export class PropertyInquiriesComponent implements OnInit {
  inquiries: any[] = [];
  propertyId!: number;

  constructor(
    private propertyInquiryService: PropertyInquiryService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.propertyId = params['id'];
      this.loadInquiries();
    });
  }

  loadInquiries(): void {
    this.propertyInquiryService.getInquiriesByProperty(this.propertyId).subscribe(inquiries => {
      this.inquiries = inquiries;
    });
  }
}
