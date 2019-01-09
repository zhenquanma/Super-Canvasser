import { Component, OnInit } from '@angular/core';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';

import { ActivatedRoute, Router, Params } from '@angular/router';
import {CurrentAssignmentService} from '../canvasser-services/canvasserCurrentAssignment.service';

@Component({
  selector: 'app-canvasser-location-info',
  templateUrl: './canvasser-location-info.component.html',
  styleUrls: ['./canvasser-location-info.component.css']
})
export class CanvasserLocationInfoComponent implements OnInit {
  currentRate = 0;
  zoom: number = 8;
// initial center position for the map
inilat: number = 0;
inilng: number = 0;

markers:Array<any>=[]

TalkingPoints=""
Questions:Array<any>=[]
speakAnswer=""

Note=""


  constructor(
    public router: Router,
    config: NgbRatingConfig,
    public CurrentAssignmentService:CurrentAssignmentService)
    {
    config.max = 5;
   }


  ngOnInit() {
    this.loadData()
  }




  public loadData(){
    this.loadMap()
    this.loadLocationPreInfo()
  }


public loadMap(){

  let locationJSON=JSON.parse(localStorage.getItem("Canvasser_LocationDetail"));

  this.markers=[JSON.parse(localStorage.getItem("Canvasser_LocationDetail"))]

  this.inilat=locationJSON.lat
  this.inilng=locationJSON.lng
}



  public loadLocationPreInfo(){

    let assignmentID=localStorage.getItem("Canvasser_AssignmentID")
    let locationID=localStorage.getItem("Canvasser_LocationID")
    let campaignID=localStorage.getItem("Canvasser_CampaignID")
    return this.CurrentAssignmentService.canvasserLoadLocationPreInfo(campaignID,assignmentID,locationID).subscribe(
      res=>{
          this.TalkingPoints=res.TalkingPoints
          this.makeQuestionsObject(res.Questions)


      }
    )
  }



  public makeQuestionsObject(Qstring){

    for (let each of Qstring){

      this.Questions.push({
          Question:each.Question,
          QuestionId:each.QuestionId,
          Answer:""
      })
    }


  }


  public postCanvasserResult(){

    let locationID=localStorage.getItem("Canvasser_LocationID")
    let assignmentID=localStorage.getItem("Canvasser_AssignmentID")
    let postDetail={
          //variable
          AssignmentID:assignmentID,
          LocationID:locationID,
          Rating:this.currentRate,
          TalkingPoints:this.TalkingPoints,
          SpeakAnswer:this.speakAnswer,
          Note:this.Note,
          Questions:this.Questions
    }
    return this.CurrentAssignmentService.canvasserPostLocationResult(postDetail).subscribe(
      res=>{

        this.router.navigate(['/CanvasserHome/CanvasserCurrentAssignment'])
        window.location.reload()

      }
    )

  }





  public jumpReturn(){

        this.router.navigate(['/CanvasserHome/CanvasserCurrentAssignment'])
          window.location.reload()
  }


}
