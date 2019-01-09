import { Component, OnInit } from '@angular/core';


import { ActivatedRoute, Router, Params } from '@angular/router';


import { AssignmentService } from '../manager-services/managerAssignment.service';
import { CampaignService } from '../manager-services/managerCampaign.service';


@Component({
  selector: 'app-manager-view-campaign',
  templateUrl: './manager-view-campaign.component.html',
  styleUrls: ['./manager-view-campaign.component.css']
})



export class ManagerViewCampaignComponent implements OnInit {


  public assignmentList:Array<any>;
  public markers:Array<any>;

    //google map variable
 
    zoom: number = 8;
    inilat: number = 0;
    inilng: number = 0;

  constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,
    public AssignmentService:AssignmentService,
    public CampaignService:CampaignService) { }

  ngOnInit() {
    localStorage.removeItem("Manager_AssignmentID")
    //localStorage.removeItem("Manager_CampaignID")
    localStorage.removeItem("Manager_StartDate")
    localStorage.removeItem("Manager_EndDate")
    this.loadData();
  }

  

  public RefreshAssignmentList(){
    this.loadAssignmentList()

  }

  public loadData(){

     //this.loadAssignmentList()
     this.loadMap()
  }


  public loadAssignmentList(){
    let campaignId=localStorage.getItem("Manager_CampaignID");
    return this.AssignmentService.manager_GetAssignmentList(campaignId).subscribe(
            res=>{
              this.assignmentList = res;
            },
            
            error => {console.log(error)},
            () => {}
    );

  }


  public loadMap(){
    let campaignId=localStorage.getItem("Manager_CampaignID");
    return this.CampaignService.manager_GetCampaignLocationsList(campaignId).subscribe(
            res=>{
              this.markers=res          
              this.inilat=this.getIniLat(res)
              this.inilng=this.getIniLng(res)
            },
            
            error => {console.log(error)},
            () => {}
    );
  }



  public getIniLat(res):any{
    
    length=res.length
    let total=0
    for (let each of res){
       total=total+each.lat
    }

    return total/length

  }

  public getIniLng(res):any{

    length=res.length
    let total=0
    for (let each of res){
       total=total+each.lng
    }
    return total/length
  }


  public jumpCreateAssignment(){
    this.router.navigate(['/ManagerHome/ManagerCreateAssignment'])
  }




public viewAssignment(assignment){
    //console.log(assignment);
    localStorage.setItem("Manager_AssignmentID",assignment.AssignmentID)
    this.router.navigate(['/ManagerHome/ManagerViewAssignment'])
}

public editAssignment(assignment){
   // console.log(assignment);
    localStorage.setItem("Manager_AssignmentID",assignment.AssignmentID)
    localStorage.setItem("Manager_CampaignID",assignment.CampaignID)
    localStorage.setItem("Manager_StartDate",assignment.StartDate)
    localStorage.setItem("Manager_EndDate",assignment.EndDate)
    this.router.navigate(['/ManagerHome/ManagerEditAssignment'])
}


public deleteAssignment(assignment){
    return this.AssignmentService.manager_DeleteAssignment(assignment.AssignmentID).subscribe(
      res=>{
        this.router.navigate(['/ManagerHome/ManagerViewCampaign'])
      }
    )
}








}

