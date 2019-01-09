import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import {UpcomingAssignmentService} from '../canvasser-services/canvasserUpcomingAssignment.service';
@Component({
  selector: 'app-canvasser-upcoming-assignment',
  templateUrl: './canvasser-upcoming-assignment.component.html',
  styleUrls: ['./canvasser-upcoming-assignment.component.css']
})
export class CanvasserUpcomingAssignmentComponent implements OnInit {


  zoom: number = 8;
  inilat: number = 0;
  inilng: number = 0;
  



  markers:Array<any>=[]
  allMarkers:Array<any>=[]
  upcomingAssignmentsList:Array<any>=[]


  constructor(
      public router: Router,
      public activeRoute: ActivatedRoute,
      public UpcomingAssignmentService:UpcomingAssignmentService
  ) { }

  ngOnInit() {
      this.loadData()
  }

  public loadData(){
      this.loadMap()
      this.loadUpcomingAssignmentList()  

  }

  public loadMap(){
    return this.UpcomingAssignmentService.canvasser_LoadUpcomingAssignmentToMap().subscribe(
      res=>{

          this.markers=res;
          this.allMarkers=res;
          this.inilat=this.getIniLat(res)
          this.inilng=this.getIniLng(res)

      },
      
      error => {console.log(error)},
      );
    
  }

  public loadUpcomingAssignmentList(){

    return this.UpcomingAssignmentService.canvasser_LoadUpcomingAssignmentList().subscribe(
      res=>{
      
        this.upcomingAssignmentsList=res;
      

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




  public viewUpcomingAssignmentInMap(upcomingAssignment){
    this.markers=upcomingAssignment.Locations

  }


  public showAllLocationInMap(){
    this.markers=this.allMarkers;
  }


}
