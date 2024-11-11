import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAdvertsComponent } from './my-adverts.component';
import { provideHttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule, TranslateService, TranslateStore } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' }); 
  }
}

describe('MyAdvertsComponent', () => {
  let component: MyAdvertsComponent;
  let fixture: ComponentFixture<MyAdvertsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        MyAdvertsComponent,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useClass: MockTranslateLoader,
        },
      }),],
      providers: [provideHttpClient(), TranslateService, TranslateStore]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyAdvertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
