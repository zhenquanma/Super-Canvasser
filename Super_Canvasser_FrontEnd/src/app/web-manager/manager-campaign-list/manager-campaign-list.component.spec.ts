import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerCampaignListComponent } from './manager-campaign-list.component';

describe('ManagerCampaignListComponent', () => {
  let component: ManagerCampaignListComponent;
  let fixture: ComponentFixture<ManagerCampaignListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerCampaignListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerCampaignListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
