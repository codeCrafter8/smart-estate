import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyAdvertComponent } from './property-advert.component';
import { provideHttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { TranslateLoader, TranslateModule, TranslateService, TranslateStore } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' }); 
  }
}

describe('PropertyAdvertComponent', () => {
  let component: PropertyAdvertComponent;
  let fixture: ComponentFixture<PropertyAdvertComponent>;

  beforeEach(async () => {
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
      providers: [provideHttpClient(), TranslateService, TranslateStore]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyAdvertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
