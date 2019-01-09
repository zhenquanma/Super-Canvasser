import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerViewCampaignComponent } from './manager-view-campaign.component';

describe('ManagerViewCampaignComponent', () => {
  let component: ManagerViewCampaignComponent;
  let fixture: ComponentFixture<ManagerViewCampaignComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerViewCampaignComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerViewCampaignComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
