import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
//import {  userInfoUnit } from '../models/user-model';
import { AdminService } from '../admin-service/admin.service';




@Component({
  selector: 'app-admin-user-list',
  templateUrl: './admin-user-list.component.html',
  styleUrls: ['./admin-user-list.component.css']
})
export class AdminUserListComponent implements OnInit {


  public UserList:Array<any>

 constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,
    public AdminService:AdminService ) {
    
   }

  ngOnInit() {
  this.UserList=[];

  localStorage.removeItem("Admin_UserID")
    
      this.loadData();

  }

  public loadData(){
    
     return this.AdminService.admin_GetUserList().subscribe(
            res=>{
             
              this.UserList = res;
            },
            
            error => {console.log(error)},
            () => {}
    );
  }



  public jumpCreateUser(){
    
       this.router.navigate(['/AdminHome/AdminCreateUser'])
	}

  public jumpEditUser(user) {

    localStorage.setItem("Admin_UserID",user.UserID)
    this.router.navigate(['/AdminHome/AdminEditUser'])

  }

  public deleteUser(user) {
   
    this.AdminService.admin_DeleteUser(user.UserID).subscribe(
      res=>{
        this.router.navigate(['/AdminHome/AdminUserList'])
        window.location.reload()
      },
      error=>{console.log(error)},
      ()=>{}
    )

  }

}
