import { TestBed } from '@angular/core/testing';

import { PropertyInquiryService } from './property-inquiry.service';

describe('PropertyInquiryService', () => {
  let service: PropertyInquiryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PropertyInquiryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
