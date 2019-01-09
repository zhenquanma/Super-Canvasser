import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerCreateCampaignComponent } from './manager-create-campaign.component';

describe('ManagerCreateCampaignComponent', () => {
  let component: ManagerCreateCampaignComponent;
  let fixture: ComponentFixture<ManagerCreateCampaignComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerCreateCampaignComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerCreateCampaignComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
