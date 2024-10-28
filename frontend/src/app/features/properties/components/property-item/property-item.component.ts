import { Component, Input } from '@angular/core';
import { Property } from '../../models/property.model';
import { CommonModule, NgIf } from '@angular/common';
import { environment } from '../../../../../environments/environment';
import { PropertyService } from '../../services/property.service';

@Component({
  selector: 'app-property-item',
  standalone: true,
  imports: [CommonModule],
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
