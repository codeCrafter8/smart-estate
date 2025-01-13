import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertConfirmationComponent } from './advert-confirmation.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' });
  }
}

describe('AdvertConfirmationComponent', () => {
  let component: AdvertConfirmationComponent;
  let fixture: ComponentFixture<AdvertConfirmationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        AdvertConfirmationComponent,
        TranslateModule.forRoot({
                loader: {
                  provide: TranslateLoader,
                  useClass: MockTranslateLoader,
              },
      }),],
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdvertConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
