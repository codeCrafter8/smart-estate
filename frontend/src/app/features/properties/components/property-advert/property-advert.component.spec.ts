import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyAdvertComponent } from './property-advert.component';

describe('PropertyAdvertComponent', () => {
  let component: PropertyAdvertComponent;
  let fixture: ComponentFixture<PropertyAdvertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PropertyAdvertComponent]
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
