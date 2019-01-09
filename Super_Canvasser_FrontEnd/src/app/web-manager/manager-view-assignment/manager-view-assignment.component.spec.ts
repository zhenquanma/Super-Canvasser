import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerViewAssignmentComponent } from './manager-view-assignment.component';

describe('ManagerViewAssignmentComponent', () => {
  let component: ManagerViewAssignmentComponent;
  let fixture: ComponentFixture<ManagerViewAssignmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerViewAssignmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerViewAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
