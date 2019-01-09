import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerEditCampaignComponent } from './manager-edit-campaign.component';

describe('ManagerEditCampaignComponent', () => {
  let component: ManagerEditCampaignComponent;
  let fixture: ComponentFixture<ManagerEditCampaignComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerEditCampaignComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerEditCampaignComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
