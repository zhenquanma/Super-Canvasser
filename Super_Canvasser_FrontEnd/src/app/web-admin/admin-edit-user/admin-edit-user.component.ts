import { Component, OnInit } from '@angular/core';

import { AdminService } from '../admin-service/admin.service';

//import {  userInfoUnit } from '../models/user-model';

import { ActivatedRoute, Router, Params } from '@angular/router';

import { Subject } from 'rxjs/Subject';

@Component({
  selector: 'app-admin-edit-user',
  templateUrl: './admin-edit-user.component.html',
  styleUrls: ['./admin-edit-user.component.css']
})
export class AdminEditUserComponent implements OnInit {


  public userWindow:any;

  public rolesSelectList:Array<any>;
  public rolesList:Array<any>;
  public isRolesSelectOpen:boolean;


  constructor(
  	public router: Router,
    public activeRoute: ActivatedRoute,
    public AdminService:AdminService) { }

  ngOnInit() {
    //this.userInfoWindow=new userInfoUnit;
    this.loadData( );
    this.userWindow={};

    this.rolesSelectList = [];
    this.rolesList=["Admin","Manager","Canvasser"]
    this.isRolesSelectOpen = false;



  }

  public loadData( ){


    let userId=localStorage.getItem("Admin_UserID")

  	 return this.AdminService.admin_EditUserLoadWindow(userId).subscribe(
            res=>{

              this.userWindow = res;
              this.rolesSelectList=res.Role;
            },

            error => {console.log(error)},
            () => {}
    );

  }



  public editUser(userInfoWindow){


     this.userWindow.Role=this.rolesSelectList

    return this.AdminService.admin_EditUser(this.userWindow).subscribe(
      res=>{
        this.router.navigate(['/AdminHome/AdminUserList'])
        window.location.reload()
      }
    )

  }










  //click the select to check the menu is drop down or not
  clickRolesSelect(){
    this.isRolesSelectOpen = !this.isRolesSelectOpen;

  }


  //choose option
  clickRoleItem(e, user) {
    const checkbox = e.target;
    const action = (checkbox.checked ? 'add' : 'remove');
    this.updateRoleSelected(action, user);
  }


  //check the option is click or not
  isRoleCheck(user) {
    return this.rolesSelectList.findIndex(value => value == user) >= 0;

  }




  // excute add or remove
  private updateRoleSelected(action, user) {
    if (action == 'add' && this.rolesSelectList.findIndex(value => value == user) == -1) {

      this.rolesSelectList.push(user);

    }
    if (action == 'remove' && this.rolesSelectList.findIndex(value => value == user) != -1) {
      this.rolesSelectList.splice(this.rolesSelectList.findIndex(value => value == user), 1);

    }

  }








}
