import { Component, OnInit } from '@angular/core';
import { Property } from '../../models/property.model';
import { PropertyService } from '../../services/property.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-my-adverts',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './my-adverts.component.html',
  styleUrl: './my-adverts.component.scss'
})
export class MyAdvertsComponent implements OnInit {
  properties: Property[] = [];
  notFoundMessage: string | null = null;

  constructor(private propertyService: PropertyService, private router: Router, private translate: TranslateService) {}

  ngOnInit(): void {
    this.loadUserProperties();
  }

  loadUserProperties(): void {
    this.propertyService.getUserProperties().subscribe(properties => {
      this.properties = properties;
      console.log(this.properties)
      this.notFoundMessage = properties.length === 0 
        ? this.translate.instant('NO_ADVERTS_YET') 
        : null;
    });
  }

  editProperty(propertyId: number): void {
    this.router.navigate(['/my-adverts/edit', propertyId]);
  }

  viewInquiries(propertyId: number): void {
    this.router.navigate(['/my-adverts/inquiries', propertyId]);
  }
}
