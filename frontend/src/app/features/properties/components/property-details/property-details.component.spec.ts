import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PropertyDetailsComponent } from './property-details.component';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClientTestingModule, HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TranslateLoader, TranslateModule, TranslateService, TranslateStore } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import { PropertyService } from '../../services/property.service';
import { Property } from '../../models/property.model';
import { HttpClient, provideHttpClient } from '@angular/common/http';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' }); 
  }
}

describe('PropertyDetailsComponent', () => {
  let component: PropertyDetailsComponent;
  let fixture: ComponentFixture<PropertyDetailsComponent>;
  let httpTestingController: HttpTestingController;
  let propertyService: PropertyService;

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
        PropertyDetailsComponent, 
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
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyDetailsComponent);
    component = fixture.componentInstance;
    httpTestingController = TestBed.inject(HttpTestingController); 
  });

  afterEach(() => {
    httpTestingController.verify(); 
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load property data', () => {
    const mockProperty = { id: 1, title: 'Big house in the city center', imageIds: [1, 2] } as Property;
    
    spyOn(component['propertyService'], 'getPropertyById').and.returnValue(of(mockProperty));

    component.loadProperty();

    expect(component.property).toBeDefined();
    expect(component.property.id).toBe(1);
    expect(component.property.title).toBe('Big house in the city center');
  });

  it('[IT test] should load property data on init', (done) => {
    const mockProperty = { id: 1 } as Property;
  
    spyOn(component['propertyService'], 'getPropertyById').and.callThrough(); 
    spyOn(component, 'updateImageDisplay');
  
    component.ngOnInit();
  
    const req = httpTestingController.expectOne(`http://localhost:8080/api/v1/properties/1`);
  
    expect(req.request.method).toEqual('GET');
    req.flush(mockProperty); 
  
    expect(component['propertyService'].getPropertyById).toHaveBeenCalledTimes(1); 
    expect(component.property).toEqual(mockProperty);
  
    done();
  });  
});
