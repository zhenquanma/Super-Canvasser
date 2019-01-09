import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router, Params } from '@angular/router';
//import {  userInfoUnit } from '../models/user-model';
import { AdminService } from '../admin-service/admin.service';



@Component({
  selector: 'app-admin-setting-list',
  templateUrl: './admin-setting-list.component.html',
  styleUrls: ['./admin-setting-list.component.css']
})
export class AdminSettingListComponent implements OnInit {

  public MaxDuration=""
  public AveSpeed=""
  constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,
    public AdminService:AdminService 
  ) { }

  ngOnInit() {
    localStorage.removeItem("Admin_UserID")
    this.loadData()
  }


public loadData(){
  this.AdminService.admin_GetMaxDuration().subscribe(
    res=>{
      this.MaxDuration=res

    },
    
    error => {console.log(error)},
    () => {}
);



this.AdminService.admin_GetAveSpeed().subscribe(
  res=>{
    this.AveSpeed=res
  },
  
  error => {console.log(error)},
  () => {}
);

}


  public editMaxDuration(){
    var x = prompt ("Change Max Duration: ", "");
    if(x==""){
      return
    }
    //alert(x)
    this.MaxDuration=x

    return this.AdminService.admin_EditMaxDuration(x).subscribe(
      res=>{
  
      },
      
      error => {console.log(error)},
      () => {}
);

    

  }

  public editAveSpeed(){
    var x = prompt ("Change Ave Speed: ", "");
    if(x==""){
      return
    }
    //alert(x)
    this.AveSpeed=x

    return this.AdminService.admin_EditAveSpeed(x).subscribe(
      res=>{
  
      },
      
      error => {console.log(error)},
      () => {}
);

  }

}
