import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LanguageSwitcherComponent } from './language-switcher.component';
import { TranslateService, TranslateStore, TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' });
  }
}

describe('LanguageSwitcherComponent', () => {
  let component: LanguageSwitcherComponent;
  let fixture: ComponentFixture<LanguageSwitcherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useClass: MockTranslateLoader,
          },
        }),
        LanguageSwitcherComponent,
      ],
      providers: [
        TranslateService,
        TranslateStore,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LanguageSwitcherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
