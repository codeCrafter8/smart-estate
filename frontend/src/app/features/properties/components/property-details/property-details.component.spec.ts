import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyDetailsComponent } from './property-details.component';
import { RouterModule } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

describe('PropertyDetailsComponent', () => {
  let component: PropertyDetailsComponent;
  let fixture: ComponentFixture<PropertyDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PropertyDetailsComponent, RouterModule.forRoot([])],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
