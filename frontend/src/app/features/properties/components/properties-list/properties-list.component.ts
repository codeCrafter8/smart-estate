import { Component, OnInit } from '@angular/core';
import { PropertyService } from '../../services/property.service';
import { Property } from '../../models/property.model';
import { PropertyItemComponent } from '../property-item/property-item.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { SearchBarComponent } from "../search-bar/search-bar.component";
import { PropertySearchCriteria } from '../../models/property-search-criteria.model';

@Component({
  selector: 'app-properties-list',
  standalone: true,
  imports: [PropertyItemComponent, CommonModule, SearchBarComponent],
  templateUrl: './properties-list.component.html',
  styleUrls: ['./properties-list.component.scss']
})
export class PropertiesListComponent implements OnInit {
  properties: Property[] = [];
  errorMessage: string | null = null;
  searchCriteria: PropertySearchCriteria = {};

  constructor(private route: ActivatedRoute, private propertyService: PropertyService) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.fetchProperties(params);
      this.searchCriteria = params;
    });
  }

  fetchProperties(criteria: any) {
    this.propertyService.searchProperties(criteria).subscribe({
      next: (properties) => {
        this.properties = properties;
        this.errorMessage = properties.length === 0 ? 'No properties found with the given criteria.' : null;
      },
      error: () => {
        this.properties = [];
        this.errorMessage = 'An error occurred while fetching properties.';
      }
    });
  }
}
