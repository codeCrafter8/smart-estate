import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PropertySearchCriteria } from '../../models/property-search-criteria.model';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, TranslateModule],
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss'],
})
export class SearchBarComponent implements OnInit {
  @Input() initialCriteria: PropertySearchCriteria | undefined;
  searchForm: FormGroup;

  propertyTypes = ['Apartment', 'House'];
  isLanguageEnglish: boolean = false;
  
  constructor(private fb: FormBuilder, private router: Router, private translate: TranslateService) {
    const currentLang = this.translate.currentLang; 
    this.isLanguageEnglish = currentLang ? currentLang === 'en' : true;

    this.searchForm = this.fb.group({
      propertyType: ['Apartment', Validators.required],  
      location: [''],
      minPrice: [null, [Validators.min(0)]],
      maxPrice: [null, [Validators.min(0)]],
      currency: [this.isLanguageEnglish ? 'USD' : 'PLN', Validators.required],
      minArea: [null, [Validators.min(0)]],
      maxArea: [null, [Validators.min(0)]],
    });
  }

  ngOnInit() {
    if (this.initialCriteria) {
      this.searchForm.patchValue(this.initialCriteria);
    }
  
  
    this.translate.onLangChange.subscribe(() => {
      this.onLangChange(); 
    });

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
      this.router.navigate(['/properties'], { queryParams: searchCriteria });
    }
  }

  getPropertyTypeTranslation(type: string): string {
    return this.translate.instant(`propertyTypes.${type}`);
  }

  onLangChange(): void {
    const currentLang = this.translate.currentLang;
    this.isLanguageEnglish = currentLang === 'en';
    this.searchForm.patchValue({ currency: this.isLanguageEnglish ? 'USD' : 'PLN' });
  }
}
