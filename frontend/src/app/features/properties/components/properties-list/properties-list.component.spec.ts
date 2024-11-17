import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertiesListComponent } from './properties-list.component';
import { provideHttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { TranslateLoader, TranslateModule, TranslateService, TranslateStore } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' }); 
  }
}

describe('PropertiesListComponent', () => {
  let component: PropertiesListComponent;
  let fixture: ComponentFixture<PropertiesListComponent>;

  beforeEach(async () => {
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
      providers: [provideHttpClient(), TranslateService, TranslateStore]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertiesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
