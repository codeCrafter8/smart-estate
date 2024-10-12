import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss'],
})
export class SearchBarComponent implements OnInit {
  searchForm: FormGroup;

  propertyTypes = ['Apartment', 'House'];
  transactionTypes = ['Buy', 'Rent'];

  constructor(private fb: FormBuilder) {
    this.searchForm = this.fb.group({
      propertyType: ['House', Validators.required], 
      transactionType: ['Buy', Validators.required], 
      location: [''],
      minPrice: [null, [Validators.min(0)]],
      maxPrice: [null, [Validators.min(0)]],
      minArea: [null, [Validators.min(0)]],
      maxArea: [null, [Validators.min(0)]],
    });
  }

  ngOnInit() {
    this.searchForm.valueChanges.subscribe(() => {
      const minPrice = this.searchForm.get('minPrice')?.value;
      const maxPrice = this.searchForm.get('maxPrice')?.value;
      const minArea = this.searchForm.get('minArea')?.value;
      const maxArea = this.searchForm.get('maxArea')?.value;

      if (minPrice !== null && minPrice < 0) {
        this.searchForm.patchValue({ minPrice: Math.abs(minPrice) }, { emitEvent: false });
      }
      if (maxPrice !== null && maxPrice < 0) {
        this.searchForm.patchValue({ maxPrice: Math.abs(maxPrice) }, { emitEvent: false });
      }
      if (minArea !== null && minArea < 0) {
        this.searchForm.patchValue({ minArea: Math.abs(minArea) }, { emitEvent: false });
      }
      if (maxArea !== null && maxArea < 0) {
        this.searchForm.patchValue({ maxArea: Math.abs(maxArea) }, { emitEvent: false });
      }
    });
  }

  onSearch() {
    if (this.searchForm.valid) {
      const searchCriteria = this.searchForm.value;
      console.log('Search Criteria:', searchCriteria);
    } else {
      console.log('Invalid form input');
    }
  }
}
