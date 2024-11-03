import { Component, OnInit } from '@angular/core';
import { Property } from '../../models/property.model';
import { PropertyService } from '../../services/property.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-adverts',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-adverts.component.html',
  styleUrl: './my-adverts.component.scss'
})
export class MyAdvertsComponent implements OnInit {
  properties: Property[] = [];

  constructor(private propertyService: PropertyService, private router: Router) {}

  ngOnInit(): void {
    this.loadUserProperties();
  }

  loadUserProperties(): void {
    this.propertyService.getUserProperties().subscribe(properties => {
      this.properties = properties;
    });
  }

  editProperty(propertyId: number): void {
    this.router.navigate(['/my-adverts/edit', propertyId]);
  }
}
