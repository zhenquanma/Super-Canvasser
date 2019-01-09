import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';



import { CampaignService } from '../manager-services/managerCampaign.service';



@Component({
  selector: 'app-manager-campaign-list',
  templateUrl: './manager-campaign-list.component.html',
  styleUrls: ['./manager-campaign-list.component.css']
})
export class ManagerCampaignListComponent implements OnInit {




public campaignList:Array<any>;

constructor(
  public router: Router,
      public activeRoute: ActivatedRoute,
      public CampaignService:CampaignService
    
    ) {
  
  }

  ngOnInit() {
    this.loadData();
    localStorage.removeItem("Manager_CampaignID")
    localStorage.removeItem("Manager_AssignmentID")

  }

public loadData(){
  return this.CampaignService.manager_GetCampaignList().subscribe(
          res=>{
            this.campaignList = res;
          },
          
          error => {console.log(error)},

  );
}

public jumpCreateCampaign(){
 
    this.router.navigate(['/ManagerHome/ManagerCreateCampaign'])



}

public viewCampaignButton(campaign){
  console.log(campaign)
  localStorage.setItem("Manager_CampaignID",campaign.CampaignID);
  console.log(localStorage)
  this.router.navigate(['/ManagerHome/ManagerViewCampaign']);
}

public editCampaignButton(campaign){
  localStorage.setItem("Manager_CampaignID",campaign.CampaignID);
  this.router.navigate(['/ManagerHome/ManagerEditCampaign'])
}

public deleteCampaignButton(campaign){
  return this.CampaignService.manager_DeleteCampaign(campaign.CampaignID).subscribe(
    res=>{
      
      this.router.navigate(['/ManagerHome/ManagerCampaignList'])
      window.location.reload()
    }
  )

}


public resultCampaignButton(campaign){
  localStorage.setItem("Manager_CampaignID",campaign.CampaignID);
  this.router.navigate(['/ManagerHome/ManagerCampaignResult'])
}
}
