import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import {CurrentAssignmentService} from '../canvasser-services/canvasserCurrentAssignment.service';
@Component({
  selector: 'app-canvasser-current-assignment',
  templateUrl: './canvasser-current-assignment.component.html',
  styleUrls: ['./canvasser-current-assignment.component.css']
})
export class CanvasserCurrentAssignmentComponent implements OnInit {

  zoom: number = 8;

  // initial center position for the map
  inilat: number = 0;
  inilng: number = 0;

  markers:Array<any>=[]

  locationsList:Array<any>=[]
  assignmentInfo:any;
  CurrentLocation={};
  NextLocation={};


  constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,
    public CurrentAssignmentService:CurrentAssignmentService
  ) { }

  ngOnInit() {
    localStorage.removeItem("Canvasser_AssignmentID")
    localStorage.removeItem("Canvasser_CampaignID")
    localStorage.removeItem("Canvasser_LocationID")
    localStorage.removeItem("Canvasser_LocationDetail")
    this.loadData()
  }

  public loadData(){
    this.loadCurrentAssignmentInfo()
    // this.loadCurrentLocation()
    // this.loadNextLocation()

    // this.loadCurrentAssignmentLocationList()

  }

public loadCurrentLocation(){

  let assignmentID=localStorage.getItem("Canvasser_AssignmentID")
  return this.CurrentAssignmentService.canvasser_LoadCurrentLocation(assignmentID).subscribe(
    res=>{
      this.CurrentLocation=res

    }
  )

}

public loadNextLocation(){

  let assignmentID=localStorage.getItem("Canvasser_AssignmentID")
  return this.CurrentAssignmentService.canvasser_LoadNextLocation(assignmentID).subscribe(
    res=>{
      this.NextLocation=res
    }
  )

}

public loadCurrentAssignmentInfo(){

  return this.CurrentAssignmentService.canvasser_LoadCurrentAssignmentInfo().subscribe(
    res=>{
      this.assignmentInfo=res
      localStorage.setItem("Canvasser_AssignmentID",res.AssignmentID)
      localStorage.setItem("Canvasser_CampaignID",res.CampaignID)

      this.loadCurrentLocation()
      this.loadNextLocation()
  
      this.loadCurrentAssignmentLocationList()
    }
  )

}

public loadCurrentAssignmentLocationList(){

  let assignmentID=localStorage.getItem("Canvasser_AssignmentID")

  return this.CurrentAssignmentService.canvasser_LoadCurrentAssignmentLocationList(assignmentID).subscribe(
    res=>{
      this.locationsList=res
      this.markers=res
      this.inilat=this.getIniLat(res)
      this.inilng=this.getIniLng(res)

    }
  )


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













  public viewLocation(location){

    localStorage.setItem("Canvasser_LocationID",location.LocationID)
    localStorage.setItem("Canvasser_LocationDetail",JSON.stringify(location))
    this.router.navigate(['/CanvasserHome/CanvasserLocationInfo'])

  }
}
