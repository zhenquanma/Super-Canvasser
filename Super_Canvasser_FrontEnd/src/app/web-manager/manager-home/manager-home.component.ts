import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-manager-home',
  templateUrl: './manager-home.component.html',
  styleUrls: ['./manager-home.component.css']
})
export class ManagerHomeComponent implements OnInit {

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
