import { Component, OnInit } from '@angular/core';

import { AssignmentService } from '../manager-services/managerAssignment.service';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { Subject } from 'rxjs/Subject';

@Component({
  selector: 'app-manager-edit-assignment',
  templateUrl: './manager-edit-assignment.component.html',
  styleUrls: ['./manager-edit-assignment.component.css']
})
export class ManagerEditAssignmentComponent implements OnInit {

public assignmentWindow:any;
public locationsList:Array<any>;
public locationsSelectList:Array<any>;


public isLocationsSelectOpen:boolean;

public canvasserList:Array<any>;

public changeDateSubject:Subject<any> = new Subject<any>();

public minDate=""
public maxDate=""

  constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,
    public AssignmentService:AssignmentService
  ) { }

  ngOnInit() {

   this.assignmentWindow={}
    this.locationsSelectList = [];
  
 
    this.locationsList=[]


    this.isLocationsSelectOpen = false;
    this.canvasserList=[];

    this.loadData();


    this.changeDateSubject
    .subscribe(k => {
 
      this.loadCanvasserAccordingToDate(k)
    });
  }


  public loadData(){
   
    this.minDate=localStorage.getItem("Manager_StartDate")
    this.maxDate=localStorage.getItem("Manager_EndDate")
    this.loadWindow()


  }


  public loadWindow(){
    let assignmentId=localStorage.getItem("Manager_AssignmentID");
    return this.AssignmentService.manager_EditAssignmentWindow(assignmentId).subscribe(
            res=>{
 
              this.assignmentWindow = res;
              this.locationsList=this.assignmentWindow.LocationsOption;
              this.locationsSelectList=this.assignmentWindow.Locations;
              this.canvasserList=this.assignmentWindow.CanvassersOption;
              
            },
            
            error => {console.log(error)},
            () => {}
    );
  }




  public changeDate($event):void{
  
    this.changeDateSubject.next(this.assignmentWindow.AssignmentDate);
  }




  public loadCanvasserAccordingToDate(date){

    // let startDate=localStorage.getItem("Manager_StartDate")
    // let endDate=localStorage.getItem("Manager_EndDate")

    let assignmentID=localStorage.getItem("Manager_AssignmentID")
    let campaignID=localStorage.getItem("Manager_CampaignID")

    // let sDateObject=new Date(startDate)
    // let eDateObject=new Date(endDate)
    return this.AssignmentService.manager_EditAssignment_LoadCanvasserAccordingToDate( campaignID,assignmentID, date).subscribe(
			res=>{

        this.canvasserList=res;
			},
			error => {console.log(error)},
			() => {}
		);
  }

  public editAssignmentBackend(){

    return this.AssignmentService.manager_EditAssignment(this.assignmentWindow).subscribe(
      res=>{
        this.router.navigate(['/ManagerHome/ManagerViewCampaign'])
      }
    )


  }







  //click the drop down menu
  clickLocationsSelect(){
    this.isLocationsSelectOpen = !this.isLocationsSelectOpen;
  }



  // click the option
  clickLocationItem(e, location) {
    const checkbox = e.target;
    const action = (checkbox.checked ? 'add' : 'remove');
    this.updateLocationSelected(action, location);
  }



  // check the option is click or not
  isLocationsCheck(location) {

    return this.locationsSelectList.findIndex(value => value == location) >= 0;

  }




  //excute or not
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
