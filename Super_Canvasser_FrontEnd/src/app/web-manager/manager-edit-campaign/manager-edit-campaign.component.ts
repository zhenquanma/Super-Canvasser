import { Component, OnInit } from '@angular/core';

import { CampaignService } from '../manager-services/managerCampaign.service';
import { ActivatedRoute, Router, Params } from '@angular/router';


import { Subject } from 'rxjs/Subject';
@Component({
  selector: 'app-manager-edit-campaign',
  templateUrl: './manager-edit-campaign.component.html',
  styleUrls: ['./manager-edit-campaign.component.css']
})
export class ManagerEditCampaignComponent implements OnInit {
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

    public CampaignService:CampaignService) { }

  ngOnInit() {

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

    this.loadWindow()

  }

  public loadWindow(){
    let campaignId=localStorage.getItem("Manager_CampaignID");
    return this.CampaignService.manager_EditCampaignWindow(campaignId).subscribe(
            res=>{
              this.campaignWindow = res;
              this.managersList=this.campaignWindow.ManagersOption;
              this.canvassersList=this.campaignWindow.CanvassersOption;
              this.managersSelectList=this.campaignWindow.Managers;
              this.canvassersSelectList=this.campaignWindow.Canvassers;
            },

            error => {console.log(error)},
            () => {}
    );
  }



  //already load original canvasser, this wiil excute when the date change

  public loadCanvasserAccordingToDate(){

    // let sDate=new Date(this.campaignWindow.StartDate).toDateString()
    // let eDate=new Date(this.campaignWindow.EndDate).toDateString()
    let sDate=this.campaignWindow.StartDate
    let eDate=this.campaignWindow.EndDate
    console.log(eDate)
    return this.CampaignService.manager_EditCampaign_LoadCanvasserAccordingToDate(sDate,eDate).subscribe(
			res=>{
        this.canvassersList=res;
        this.canvassersSelectList=[];

			},
			error => {console.log(error)},
			() => {}
		);
  }




//listen to the change date event
public changeDate($event):void{
    this.changeDateSubject.next();
  }



  //connect to edit campaign backend
  public editCampaignBackend(){
    // this.campaignWindow.StartDate=new Date(this.campaignWindow.StartDate).toDateString()
    // this.campaignWindow.EndDate=new Date(this.campaignWindow.EndDate).toDateString()
    return this.CampaignService.manager_EditCampaign(this.campaignWindow).subscribe(
      res=>{
        localStorage.removeItem("CampaignID")
        this.router.navigate(['/ManagerHome/ManagerCampaignList'])
        window.location.reload()
      }
    )
  }





  // click to show the menu drop down or not
  clickManagersSelect(){
    this.isManagersSelectOpen = !this.isManagersSelectOpen;
  }

  clickCanvassersSelect(){
    this.isCanvassersSelectOpen = !this.isCanvassersSelectOpen;
  }



  // click the option
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


  //excute add or remove
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
