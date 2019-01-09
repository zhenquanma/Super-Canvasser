import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasserCurrentAssignmentComponent } from './canvasser-current-assignment.component';

describe('CanvasserCurrentAssignmentComponent', () => {
  let component: CanvasserCurrentAssignmentComponent;
  let fixture: ComponentFixture<CanvasserCurrentAssignmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanvasserCurrentAssignmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasserCurrentAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
