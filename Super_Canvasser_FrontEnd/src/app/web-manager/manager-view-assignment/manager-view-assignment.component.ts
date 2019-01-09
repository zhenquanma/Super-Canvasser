import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router, Params } from '@angular/router';
import {AssignmentService} from '../manager-services/managerAssignment.service';
@Component({
  selector: 'app-manager-view-assignment',
  templateUrl: './manager-view-assignment.component.html',
  styleUrls: ['./manager-view-assignment.component.css']
})
export class ManagerViewAssignmentComponent implements OnInit {

  locationList=[]
  assignmentInfo={}






zoom: number = 8;
  
// initial center position for the map
inilat: number = 0;
inilng: number = 0;




markers: Array<any> =[];

allMarkers:Array<any>=[];



  constructor(public router: Router,
             public AssignmentService:AssignmentService) { }

  ngOnInit() {
    this.loadData()
  }
  
  public loadData(){
    this.loadAssignmentInfo()
    this.loadLocation()
  }
  
 public loadAssignmentInfo(){
   let assignmentId=localStorage.getItem("Manager_AssignmentID")
  return this.AssignmentService.manager_GetAssignmentInfo(assignmentId).subscribe(
    res=>{
      this.assignmentInfo = res;
    },  
    error => {console.log(error)},
  );

 }



 public loadLocation(){
   let assignmentId=localStorage.getItem("Manager_AssignmentID")
  return this.AssignmentService.manager_GetAssignmentAllLocation(assignmentId).subscribe(
    res=>{
      this.locationList = res;

      this.markers=res;
      this.allMarkers=res;
      this.inilat=this.getIniLat(res)
      this.inilng=this.getIniLng(res)

    },
    
    error => {console.log(error)},
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






  public viewLocationInMap(location){
 


      let tempmarkers={
      lat: location.lat,
      lng: location.lng,
      label: location.label,
      Info:location.Info,
     
      LocationName:location.LocationName,
      Status:location.Status,
      Rating:location.Rating


    }

    this.markers=[tempmarkers]
  
  }
  

  public showAllLocationInMap(){
    this.markers=this.allMarkers;
  }
  
  public jumpReturn(){
    this.router.navigate(['/ManagerHome/ManagerViewCampaign']);
    
  }
      
}
