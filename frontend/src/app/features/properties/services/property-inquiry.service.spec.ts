import { TestBed } from '@angular/core/testing';

import { PropertyInquiryService } from './property-inquiry.service';
import { provideHttpClient } from '@angular/common/http';

describe('PropertyInquiryService', () => {
  let service: PropertyInquiryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient()]
    });
    service = TestBed.inject(PropertyInquiryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
