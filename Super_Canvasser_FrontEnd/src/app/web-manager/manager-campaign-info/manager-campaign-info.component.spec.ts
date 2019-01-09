import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerCampaignInfoComponent } from './manager-campaign-info.component';

describe('ManagerCampaignInfoComponent', () => {
  let component: ManagerCampaignInfoComponent;
  let fixture: ComponentFixture<ManagerCampaignInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerCampaignInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerCampaignInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
