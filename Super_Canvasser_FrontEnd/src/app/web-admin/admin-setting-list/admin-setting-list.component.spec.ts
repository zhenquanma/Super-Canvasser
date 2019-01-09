import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSettingListComponent } from './admin-setting-list.component';

describe('AdminSettingListComponent', () => {
  let component: AdminSettingListComponent;
  let fixture: ComponentFixture<AdminSettingListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminSettingListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminSettingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
