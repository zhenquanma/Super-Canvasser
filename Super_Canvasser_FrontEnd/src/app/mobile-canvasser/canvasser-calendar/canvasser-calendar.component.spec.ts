import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasserCalendarComponent } from './canvasser-calendar.component';

describe('CanvasserCalendarComponent', () => {
  let component: CanvasserCalendarComponent;
  let fixture: ComponentFixture<CanvasserCalendarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanvasserCalendarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasserCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
