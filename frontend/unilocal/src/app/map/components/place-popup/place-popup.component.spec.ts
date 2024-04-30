import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlacePopupComponent } from './place-popup.component';

describe('PlacePopupComponent', () => {
  let component: PlacePopupComponent;
  let fixture: ComponentFixture<PlacePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlacePopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PlacePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
