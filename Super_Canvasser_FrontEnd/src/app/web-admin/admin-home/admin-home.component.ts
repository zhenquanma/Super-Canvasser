import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

  constructor() { }

  ngOnInit() {

    localStorage.removeItem("Manager_CampaignID");
    localStorage.removeItem("Manager_AssignmentID");
    localStorage.removeItem("Manager_StartDate")
    localStorage.removeItem("Manager_EndDate")



    localStorage.removeItem("Canvasser_CampaignID");
    localStorage.removeItem("Canvasser_AssignmentID");
    localStorage.removeItem("Canvasser_LocationID");
    localStorage.removeItem("Canvasser_LocationDetail")

    localStorage.removeItem("Admin_UserID")

  }

}
