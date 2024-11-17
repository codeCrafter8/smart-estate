import { Component, Input } from '@angular/core';
import { Property } from '../../models/property.model';
import { CommonModule } from '@angular/common';
import { PropertyService } from '../../services/property.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-property-item',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './property-item.component.html',
  styleUrl: './property-item.component.scss'
})
export class PropertyItemComponent {
  @Input() property!: Property;

  constructor(private propertyService: PropertyService) {}

  getImageUrl(imageId: number): string {
    return this.propertyService.getImageUrl(imageId);
  }
}
