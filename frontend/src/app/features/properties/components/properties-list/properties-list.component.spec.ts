import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertiesListComponent } from './properties-list.component';
import { provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { TranslateLoader, TranslateModule, TranslateService, TranslateStore } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import { Property } from '../../models/property.model';
import { PropertyService } from '../../services/property.service';
import { PropertySearchCriteria } from '../../models/property-search-criteria.model';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' }); 
  }
}

describe('PropertiesListComponent', () => {
  let component: PropertiesListComponent;
  let fixture: ComponentFixture<PropertiesListComponent>;
  let propertyService: PropertyService;
  let translateService: TranslateService;

  beforeEach(async () => {
    const activatedRouteMock = {
      queryParams: of({ title: 'Test', country: 'USA' }), 
    };

    await TestBed.configureTestingModule({
      imports: [
        PropertiesListComponent, 
        RouterModule.forRoot([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useClass: MockTranslateLoader,
        },
      }),],
      providers: [
        provideHttpClient(), 
        TranslateService, 
        TranslateStore,
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        PropertyService,
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertiesListComponent);
    component = fixture.componentInstance;
    propertyService = TestBed.inject(PropertyService);
    translateService = TestBed.inject(TranslateService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize search criteria from query params', () => {
    component.ngOnInit();
    expect(component.searchCriteria).toEqual({ title: 'Test', country: 'USA' } as Property);
  });

  it('should fetch properties successfully with mocking', () => {
    const mockProperties: Property[] = [
      { id: 1, title: 'Test Property', country: 'USA', description: 'Test Description' } as Property,
    ];

    spyOn(propertyService, 'searchProperties').and.returnValue(of(mockProperties));
    spyOn(translateService, 'instant').and.returnValue('No properties found');

    component.fetchProperties({ title: 'Test', country: 'USA' } as PropertySearchCriteria);
    
    expect(component.properties).toEqual(mockProperties);
    expect(component.errorMessage).toBeNull();
  });

  it('should handle empty properties list with mocking', () => {
    spyOn(propertyService, 'searchProperties').and.returnValue(of([]));
    spyOn(translateService, 'instant').and.returnValue('No properties found');

    component.fetchProperties({ title: 'Test', country: 'USA' } as PropertySearchCriteria);
    
    expect(component.properties).toEqual([]);
    expect(component.errorMessage).toBe('No properties found');
  });
});
