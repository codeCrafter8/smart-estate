import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAdvertsComponent } from './my-adverts.component';
import { provideHttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule, TranslateService, TranslateStore } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';

class MockTranslateLoader implements TranslateLoader {
  getTranslation(lang: string): Observable<any> {
    return of({ key: 'value' });
  }
}

describe('MyAdvertsComponent', () => {
  let component: MyAdvertsComponent;
  let fixture: ComponentFixture<MyAdvertsComponent>;
  let router: Router;

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
      providers: [
        provideHttpClient(),
        TranslateService,
        TranslateStore,
        { provide: Router, useValue: { navigate: jasmine.createSpy('navigate') } },
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyAdvertsComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to edit property route', () => {
    const propertyId = 1;

    component.navigateToEditProperty(propertyId);

    expect(router.navigate).toHaveBeenCalledWith(['/my-adverts/edit', propertyId]);
  });
});
