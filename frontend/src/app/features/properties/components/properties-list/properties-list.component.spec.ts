import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertiesListComponent } from './properties-list.component';
import { provideHttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';

describe('PropertiesListComponent', () => {
  let component: PropertiesListComponent;
  let fixture: ComponentFixture<PropertiesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PropertiesListComponent, RouterModule.forRoot([])],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertiesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
