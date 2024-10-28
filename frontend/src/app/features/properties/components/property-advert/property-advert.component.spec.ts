import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyAdvertComponent } from './property-advert.component';
import { provideHttpClient } from '@angular/common/http';

describe('PropertyAdvertComponent', () => {
  let component: PropertyAdvertComponent;
  let fixture: ComponentFixture<PropertyAdvertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PropertyAdvertComponent],
      providers: [provideHttpClient()]
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
