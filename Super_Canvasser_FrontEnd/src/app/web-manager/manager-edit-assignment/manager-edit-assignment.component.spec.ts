import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerEditAssignmentComponent } from './manager-edit-assignment.component';

describe('ManagerEditAssignmentComponent', () => {
  let component: ManagerEditAssignmentComponent;
  let fixture: ComponentFixture<ManagerEditAssignmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerEditAssignmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerEditAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
