import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginUnitComponent } from './login-unit.component';

describe('LoginUnitComponent', () => {
  let component: LoginUnitComponent;
  let fixture: ComponentFixture<LoginUnitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginUnitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginUnitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
