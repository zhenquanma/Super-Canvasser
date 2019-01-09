import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasserHomeComponent } from './canvasser-home.component';

describe('CanvasserHomeComponent', () => {
  let component: CanvasserHomeComponent;
  let fixture: ComponentFixture<CanvasserHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanvasserHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasserHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
