import { Component, OnInit } from '@angular/core';


import { ActivatedRoute, Router, Params } from '@angular/router';

import { AssignmentService } from '../manager-services/managerAssignment.service';


import { Subject } from 'rxjs/Subject';


@Component({
  selector: 'app-manager-create-assignment',
  templateUrl: './manager-create-assignment.component.html',
  styleUrls: ['./manager-create-assignment.component.css']
})
export class ManagerCreateAssignmentComponent implements OnInit {

  public assignmentWindow:any;
  public locationsList:Array<any>;


  public locationsSelectList:Array<any>;


  public isLocationsSelectOpen:boolean;

  public changeDateSubject:Subject<any> = new Subject<any>();

  public canvasserList:Array<any>;

  constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,

    public AssignmentService:AssignmentService
  ) { 

      
  }

  ngOnInit() {

    this.assignmentWindow={};

    

    this.locationsSelectList = [];
  
 
    this.locationsList=[]


    this.isLocationsSelectOpen = false;

    this.canvasserList=[];

    this.loadData()

    this.changeDateSubject
    .subscribe(k => {

      this.loadCanvasserAccordingToDate(k)
    });

  }


  public loadCanvasserAccordingToDate(date){

    let tdate=(new Date(this.assignmentWindow.AssignmentDate)).toDateString() 
    let campaignID=localStorage.getItem("CampaignID")
    return this.AssignmentService.manager_CreateAssignment_LoadCanvasserAccordingToDate(campaignID,  tdate ).subscribe(
			res=>{

        this.canvasserList=res;
			},
			error => {console.log(error)},
			() => {}
		);
  }




public loadData(){


  this.loadLocation()
}


public loadLocation(){
  
  let campaignID=localStorage.getItem("CampaignID")
  return this.AssignmentService.manager_CreateAssignment_LoadLocations(campaignID).subscribe(
    res=>{
        this.locationsList=res
    }
  )

}


public createAssignmentBackend(){

  this.assignmentWindow.AssignmentDate=new Date(this.assignmentWindow.AssignmentDate).toDateString()
  let campaignID=localStorage.getItem("CampaignID")
  return this.AssignmentService.manager_PostAssignment(campaignID,this.assignmentWindow).subscribe(
    res=>{
      localStorage.removeItem("AssignmentID")
      this.router.navigate(['/ManagerHome/ManagerViewCampaign'])
    }
  )


}





public changeDate($event):void{
  
  this.changeDateSubject.next(this.assignmentWindow.AssignmentDate);
}



  // click the drop down menu
  clickLocationsSelect(){
    this.isLocationsSelectOpen = !this.isLocationsSelectOpen;
  }



  // click the menu option
  clickLocationItem(e, location) {
    const checkbox = e.target;
    const action = (checkbox.checked ? 'add' : 'remove');
    this.updateLocationSelected(action, location);
  }



  // check the option is click or not
  isLocationsCheck(location) {

    return this.locationsSelectList.findIndex(value => value == location) >= 0;

  }




  //excute add or not
  private updateLocationSelected(action, location) {
    if (action == 'add' && this.locationsSelectList.findIndex(value => value == location) == -1) {

      this.locationsSelectList.push(location);

    }
    if (action == 'remove' && this.locationsSelectList.findIndex(value => value == location) != -1) {
      this.locationsSelectList.splice(this.locationsSelectList.findIndex(value => value == location), 1);

    }

  }




  public jumpReturn(){
    this.router.navigate(['/ManagerHome/ManagerViewCampaign'])
  }

}
