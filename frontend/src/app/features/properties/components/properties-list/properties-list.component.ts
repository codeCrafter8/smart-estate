import { Component, Input, OnInit } from '@angular/core';
import { PropertyService } from '../../services/property.service';
import { Property } from '../../models/property';
import { PropertyItemComponent } from '../property-item/property-item.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-properties-list',
  standalone: true,
  imports: [PropertyItemComponent, CommonModule],
  templateUrl: './properties-list.component.html',
  styleUrl: './properties-list.component.scss'
})
export class PropertiesListComponent implements OnInit {
  properties: Property[] = [];
  isLoading = true;
  errorMessage: string | null = null;

  constructor(private route: ActivatedRoute, private propertyService: PropertyService) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.fetchProperties(params);
    });
  }

  fetchProperties(criteria: any) {
    this.isLoading = true;
    this.propertyService.searchProperties(criteria).subscribe({
      next: (properties) => {
        this.properties = properties;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'No properties found or an error occurred.';
        this.isLoading = false;
      }
    });
  }
}
