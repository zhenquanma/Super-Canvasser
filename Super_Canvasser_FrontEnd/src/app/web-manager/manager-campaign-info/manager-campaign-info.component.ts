import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { CampaignService } from '../manager-services/managerCampaign.service';

@Component({
  selector: 'app-manager-campaign-info',
  templateUrl: './manager-campaign-info.component.html',
  styleUrls: ['./manager-campaign-info.component.css']
})
export class ManagerCampaignInfoComponent implements OnInit {


  public campaignInfo:any;

  constructor(
    public router:Router,
    public activeRoute:ActivatedRoute,
    public CampaignService:CampaignService
  ) { 
    

  }

  ngOnInit() {

      this.loadData();
 
  }


  public loadData(){
    
    let campaignID= localStorage.getItem("Manager_CampaignID")
    return this.CampaignService.manager_GetCampaignInfo(campaignID).subscribe(
            res=>{
    
              this.campaignInfo=res;
            },
            
            error => {console.log(error)},
            () => {}
    );
  }


}
