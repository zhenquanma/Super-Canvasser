import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerCreateAssignmentComponent } from './manager-create-assignment.component';

describe('ManagerCreateAssignmentComponent', () => {
  let component: ManagerCreateAssignmentComponent;
  let fixture: ComponentFixture<ManagerCreateAssignmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerCreateAssignmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerCreateAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
