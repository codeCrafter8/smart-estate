import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertConfirmationComponent } from './advert-confirmation.component';

describe('AdvertConfirmationComponent', () => {
  let component: AdvertConfirmationComponent;
  let fixture: ComponentFixture<AdvertConfirmationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvertConfirmationComponent]
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
