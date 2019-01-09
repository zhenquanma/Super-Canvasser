import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerCampaignResultComponent } from './manager-campaign-result.component';

describe('ManagerCampaignResultComponent', () => {
  let component: ManagerCampaignResultComponent;
  let fixture: ComponentFixture<ManagerCampaignResultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerCampaignResultComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerCampaignResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
