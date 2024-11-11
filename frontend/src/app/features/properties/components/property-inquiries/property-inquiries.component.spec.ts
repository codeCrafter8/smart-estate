import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyInquiriesComponent } from './property-inquiries.component';
import { provideHttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';

describe('PropertyInquiriesComponent', () => {
  let component: PropertyInquiriesComponent;
  let fixture: ComponentFixture<PropertyInquiriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PropertyInquiriesComponent, RouterModule.forRoot([])],
      providers: [provideHttpClient()]
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
