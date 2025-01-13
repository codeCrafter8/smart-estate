import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyAdvertComponent } from './property-advert.component';
import { provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { TranslateLoader, TranslateModule, TranslateService, TranslateStore } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import { PropertyService } from '../../services/property.service';
import { Property } from '../../models/property.model';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' }); 
  }
}

describe('PropertyAdvertComponent', () => {
  let component: PropertyAdvertComponent;
  let fixture: ComponentFixture<PropertyAdvertComponent>;
  let propertyService: PropertyService;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: (key: string) => '1', 
        },
      },
    };

    await TestBed.configureTestingModule({
      imports: [
        PropertyAdvertComponent, 
        RouterModule.forRoot([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useClass: MockTranslateLoader,
        },
      }),],
      providers: [
        provideHttpClient(), 
        provideHttpClientTesting(),
        TranslateService, 
        TranslateStore,
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        PropertyService, 
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyAdvertComponent);
    component = fixture.componentInstance;
    propertyService = TestBed.inject(PropertyService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form on component init', () => {
    expect(component.propertyForm).toBeDefined();
    expect(component.propertyForm.valid).toBeFalse();
  });

  it('should call propertyService.getPropertyById() when in edit mode with mocking', () => {
    spyOn(propertyService, 'getPropertyById').and.returnValue(of({ title: 'Test Property', country: 'Test Country', description: 'Test Description', imageIds: [1] } as Property));
    
    component.propertyId = 1;
    component.ngOnInit();
    expect(propertyService.getPropertyById).toHaveBeenCalledWith(1);
  });

  it('should generate description with mocking', () => {
    const generatedDescription = 'Generated description';
    spyOn(component['propertyService'], 'generateDescription').and.returnValue(of({ description: generatedDescription }));
    component.generateDescription();
    expect(component.propertyForm.value.description).toBe(generatedDescription);
  });

  it('should interact with service on form submit', () => {
    component.propertyForm.patchValue({
      propertyType: 'House',
      title: 'New Property',
      country: 'New Country',
      address: 'Some Address',
      yearBuilt: 2020,
      totalBuildingFloors: 5,
      apartmentFloor: 2,
      totalRooms: 4,
      totalBedrooms: 2,
      totalBathrooms: 2,
      area: 100,
      priceAmount: 100000,
      currency: 'USD',
      images: ['image1.jpg'],
      description: 'New Description'
    });
    component.onSubmit();

    const req = httpTestingController.expectOne('http://localhost:8080/api/v1/properties');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(component.propertyForm.value);

    req.flush({ id: 1 });
  });

  it('should interact with service to update property', () => {
    component.propertyForm.patchValue({
      propertyType: 'House',
      title: 'Updated Property',
      country: 'New Country',
      address: 'Some Address',
      yearBuilt: 2020,
      totalBuildingFloors: 5,
      apartmentFloor: 2,
      totalRooms: 4,
      totalBedrooms: 2,
      totalBathrooms: 2,
      area: 100,
      priceAmount: 100000,
      currency: 'USD',
      images: ['image1.jpg'],
      description: 'Updated Description'
    });
    component.isEditMode = true;
    component.propertyId = 1;
  
    component.onSubmit();
  
    const req = httpTestingController.expectOne('http://localhost:8080/api/v1/properties/1');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(component.propertyForm.value);

    req.flush({ id: 1 });
  });
});

