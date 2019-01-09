import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasserLocationInfoComponent } from './canvasser-location-info.component';

describe('CanvasserLocationInfoComponent', () => {
  let component: CanvasserLocationInfoComponent;
  let fixture: ComponentFixture<CanvasserLocationInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanvasserLocationInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasserLocationInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
