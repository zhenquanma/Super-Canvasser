import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasserUpcomingAssignmentComponent } from './canvasser-upcoming-assignment.component';

describe('CanvasserUpcomingAssignmentComponent', () => {
  let component: CanvasserUpcomingAssignmentComponent;
  let fixture: ComponentFixture<CanvasserUpcomingAssignmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanvasserUpcomingAssignmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasserUpcomingAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
