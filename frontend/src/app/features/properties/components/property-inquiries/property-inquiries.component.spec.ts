import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyInquiriesComponent } from './property-inquiries.component';
import { provideHttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { TranslateLoader, TranslateModule, TranslateService, TranslateStore } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' }); 
  }
}

describe('PropertyInquiriesComponent', () => {
  let component: PropertyInquiriesComponent;
  let fixture: ComponentFixture<PropertyInquiriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        PropertyInquiriesComponent, 
        RouterModule.forRoot([]),
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useClass: MockTranslateLoader,
        },
      }),],
      providers: [provideHttpClient(), TranslateService, TranslateStore]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyInquiriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
