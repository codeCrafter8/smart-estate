import { Component, Input } from '@angular/core';
import { Property } from '../../models/property';
import { CommonModule, NgIf } from '@angular/common';

@Component({
  selector: 'app-property-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './property-item.component.html',
  styleUrl: './property-item.component.scss'
})
export class PropertyItemComponent {
  @Input() property!: Property;
}
