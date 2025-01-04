import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyDetailsComponent } from './property-details.component';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule, TranslateService, TranslateStore } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import { PropertyService } from '../../services/property.service';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' }); 
  }
}

describe('PropertyDetailsComponent', () => {
  let component: PropertyDetailsComponent;
  let fixture: ComponentFixture<PropertyDetailsComponent>;

  beforeEach(async () => {
    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: (key: string) => '1', 
        },
      },
    };

    const propertyServiceMock = jasmine.createSpyObj('PropertyService', ['getPropertyById']);
    propertyServiceMock.getPropertyById.and.returnValue(of({ id: 1, title: 'Big house in the city center' }));

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
        TranslateService, 
        TranslateStore,
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        { provide: PropertyService, useValue: propertyServiceMock },
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load property on init', () => {
    component.ngOnInit();

    expect(component.property).toBeDefined();
    expect(component.property.id).toBe(1);
    expect(component.property.title).toBe('Big house in the city center');
  });
});
