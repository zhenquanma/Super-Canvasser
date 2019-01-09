import { Component, OnInit } from '@angular/core';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';

import { CampaignResultService} from '../manager-services/managerCampaignResult.service';
import { ActivatedRoute, Router, Params } from '@angular/router';

@Component({
  selector: 'app-manager-campaign-result',
  templateUrl: './manager-campaign-result.component.html',
  styleUrls: ['./manager-campaign-result.component.css']
})
export class ManagerCampaignResultComponent implements OnInit {

  public staticResult={};


  public locationsList:Array<any>;
  public questionsList:Array<any>;


  //map
  zoom: number = 8;
  // initial center position for the map
  inilat: number = 0;
  inilng: number = 0;


  // this list will show in the map
  markers: Array<any> =[];

  //back up for all markers
  allMarkers:Array<any>=[];

  constructor(config: NgbRatingConfig,
    public router: Router,
    public activeRoute: ActivatedRoute,
    public CampaignResultService:CampaignResultService) {

      //rating config
    config.max = 5;
    config.readonly = true;


   }

  ngOnInit() {

    this.loadData();
  }

   
  public loadData(){
    this.loadStatisticalData();
    this.loadLocationData();
    this.loadQuestionData();
    
    
  }


  public loadStatisticalData(){
    let campaignID=localStorage.getItem("Manager_CampaignID")
    return this.CampaignResultService.manager_GetCampaignStatisticalResult(campaignID).subscribe(
      res=>{
        this.staticResult = res;
      },  
      error => {console.log(error)},
    );

  }

  public loadLocationData(){
    let campaignID=localStorage.getItem("Manager_CampaignID")
    return this.CampaignResultService.manager_GetCampaignLocationResult(campaignID).subscribe(
      res=>{
        this.locationsList = res;

        this.markers=res;
        this.allMarkers=res;

        //to get ini position
        this.inilat=this.getIniLat(res)
        this.inilng=this.getIniLng(res)

      },
      
      error => {console.log(error)},
      );
  }

  



//get the init lat
  public getIniLat(res):any{
    
    length=res.length
    let total=0
    for (let each of res){
       total=total+each.lat
    }

    return total/length

  }


  //get the init lng
  public getIniLng(res):any{
    length=res.length
    let total=0
    for (let each of res){
       total=total+each.lng
    }
    return total/length

  }




  public loadQuestionData(){
    let campaignID=localStorage.getItem("Manager_CampaignID")
    return this.CampaignResultService.manager_GetCampaignQuestionResult(campaignID).subscribe(
      res=>{
        this.questionsList = res;
      },
      error => {console.log(error)},
      );
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


  //click show all location button
  public showAllLocationInMap(){
    this.markers=this.allMarkers;
  }

}
