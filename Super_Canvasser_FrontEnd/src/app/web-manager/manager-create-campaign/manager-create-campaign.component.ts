import { Component, OnInit, Input, Output } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';

import { CampaignService } from '../manager-services/managerCampaign.service';
import { EventEmitter } from '@angular/core';

import { Subject } from 'rxjs/Subject';


@Component({
  selector: 'app-manager-create-campaign',
  templateUrl: './manager-create-campaign.component.html',
  styleUrls: ['./manager-create-campaign.component.css']
})
export class ManagerCreateCampaignComponent implements OnInit {
  public limitStartDate=""
  
  public limitEndDate=""

  public today=this.getToday()
  
  public campaignWindow:any;
  public managersList:Array<any>;
  public canvassersList:Array<any>;
  public managersSelectList:Array<any>;
  public canvassersSelectList:Array<any>;
  public isManagersSelectOpen:boolean;
  public isCanvassersSelectOpen:boolean;
  public changeDateSubject:Subject<any> = new Subject<any>();




  constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,
    public CampaignService:CampaignService
  ) {

  }

  ngOnInit() {
    // this.limitStartDate=this.getToday()
    
    this.campaignWindow={}

    this.managersSelectList = [];
    this.canvassersSelectList=[];

    this.managersList=[]
    this.canvassersList=[]

    this.isManagersSelectOpen = false;
    this.isCanvassersSelectOpen=false;

    this.loadData();


    this.changeDateSubject
      .subscribe(k => {
      console.log(k);
      this.loadCanvasserAccordingToDate()
    });



  }



  public getToday(){
    let myDate=new Date()
    console.log(myDate)
    let date=myDate.getDate()
    let month=myDate.getMonth()
    let year=myDate.getFullYear()

    return this.pad(year)+'-'+this.pad(month+1)+'-'+this.pad(date)
    
    
  }

  public pad(n){
    return n<10?'0'+n:n
  }


  public loadData(){

    this.loadManager()


  }



  //canvasser list will change according to the date
  public loadCanvasserAccordingToDate(){//yyyy-mm-dd
 console.log(this.campaignWindow.StartDate)
    // let sDate=new Date(this.campaignWindow.StartDate).toDateString()
    // let eDate=new Date(this.campaignWindow.EndDate).toDateString()
  let sDate=this.campaignWindow.StartDate
let eDate=this.campaignWindow.EndDate
    return this.CampaignService.manager_CreateCampaign_LoadCanvasserAccordingToDate(sDate,eDate).subscribe(
			res=>{

        this.canvassersList=res;
        this.canvassersSelectList=[];

			},
			error => {console.log(error)},
			() => {}
		);
  }


  //load the manager
  public loadManager(){

    return this.CampaignService.manager_CreateCampaign_LoadManager().subscribe(
			res=>{

        this.managersList=res;
        this.managersSelectList=[];
        //add itself to selectlist later
			},
			error => {console.log(error)},
			() => {}
		);

  }


  //listen to the change date event
  public changeDate($event):void{

    this.changeDateSubject.next();
  }







  //connect to backend and post new campaign
  public createCampaignBackend(){

    this.campaignWindow.Canvassers=this.canvassersSelectList;
    this.campaignWindow.Managers=this.managersSelectList;

   // console.log(this.campaignWindow.StartDate)
    //console.log(new Date(this.campaignWindow.StartDate).toDateString())
    return this.CampaignService.manager_PostCampaign(this.campaignWindow).subscribe(
      res=>{

        this.router.navigate(['/ManagerHome/ManagerCampaignList'])
        window.location.reload()
      }
    )


  }






  //click the select to check the menu is drop down or not
  clickManagersSelect(){
    this.isManagersSelectOpen = !this.isManagersSelectOpen;

  }

  clickCanvassersSelect(){
    this.isCanvassersSelectOpen = !this.isCanvassersSelectOpen;
  }



  //choose option
  clickManagerItem(e, user) {
    const checkbox = e.target;
    const action = (checkbox.checked ? 'add' : 'remove');
    this.updateManagerSelected(action, user);
  }


  clickCanvasserItem(e, user) {
    const checkbox = e.target;
    const action = (checkbox.checked ? 'add' : 'remove');
    this.updateCanvasserSelected(action, user);
  }

  //check the option is click or not
  isManagersCheck(user) {
    return this.managersSelectList.findIndex(value => value == user) >= 0;

  }

  isCanvassersCheck(user) {
    return this.canvassersSelectList.findIndex(value => value == user) >= 0;

  }


  // excute add or remove
  private updateManagerSelected(action, user) {
    if (action == 'add' && this.managersSelectList.findIndex(value => value == user) == -1) {

      this.managersSelectList.push(user);

    }
    if (action == 'remove' && this.managersSelectList.findIndex(value => value == user) != -1) {
      this.managersSelectList.splice(this.managersSelectList.findIndex(value => value == user), 1);

    }

  }



  private updateCanvasserSelected(action, user) {
    if (action == 'add' && this.canvassersSelectList.findIndex(value => value == user) == -1) {

      this.canvassersSelectList.push(user);

    }
    if (action == 'remove' && this.canvassersSelectList.findIndex(value => value == user) != -1) {
      this.managersSelectList.splice(this.canvassersSelectList.findIndex(value => value == user), 1);

    }

  }



  public jumpReturn(){
   this.router.navigate(['/ManagerHome/ManagerCampaignList'])
   window.location.reload()
  }

}
