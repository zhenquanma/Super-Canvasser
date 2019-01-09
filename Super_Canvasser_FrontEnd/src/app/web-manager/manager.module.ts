import { NgModule } from '@angular/core';

import { RouterModule } from '@angular/router';
import { ManagerHomeComponent }   from './manager-home/manager-home.component';

import {CommonModule} from '@angular/common';

import {MyManagerRoutes} from './manager.routes';
import { ManagerCampaignListComponent } from './manager-campaign-list/manager-campaign-list.component';

import { ManagerCreateCampaignComponent } from './manager-create-campaign/manager-create-campaign.component';
import { ManagerCreateAssignmentComponent } from './manager-create-assignment/manager-create-assignment.component';
import { ManagerEditCampaignComponent } from './manager-edit-campaign/manager-edit-campaign.component';
import { ManagerEditAssignmentComponent } from './manager-edit-assignment/manager-edit-assignment.component';
import { ManagerViewCampaignComponent } from './manager-view-campaign/manager-view-campaign.component';

import { CampaignService } from './manager-services/managerCampaign.service';
import { AssignmentService } from './manager-services/managerAssignment.service';
import { CampaignResultService} from './manager-services/managerCampaignResult.service';


import { HttpModule,JsonpModule ,Http} from '@angular/http';
import {HttpClientModule} from "@angular/common/http";
import { FormsModule } from '@angular/forms';
import { ManagerCampaignInfoComponent } from './manager-campaign-info/manager-campaign-info.component';
import { ManagerViewAssignmentComponent } from './manager-view-assignment/manager-view-assignment.component';
import { ManagerCampaignResultComponent } from './manager-campaign-result/manager-campaign-result.component';

import { ManagerMapComponent } from './manager-map/manager-map.component';

import { BrowserModule } from '@angular/platform-browser';
import {ApplicationRef } from '@angular/core';


import {NgbModule} from '@ng-bootstrap/ng-bootstrap';



import { AgmCoreModule } from '@agm/core';
@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        RouterModule.forChild(MyManagerRoutes),
        HttpModule,
        JsonpModule,
        FormsModule,
        HttpClientModule ,
        AgmCoreModule.forRoot({
          apiKey: 'AIzaSyCSBQ5PQYQZwAxqE08kxZlQKR1yuwu7Xnc'
        }),
        NgbModule
    ],
    exports: [],
    declarations: [
        ManagerHomeComponent,
        ManagerCampaignListComponent,
        ManagerCreateCampaignComponent,
        ManagerCreateAssignmentComponent,
        ManagerEditCampaignComponent,
        ManagerEditAssignmentComponent,
        ManagerViewCampaignComponent,
        ManagerCampaignInfoComponent,
        ManagerViewAssignmentComponent,
        ManagerCampaignResultComponent,


    ],
    providers: [
        CampaignService,
        AssignmentService,
        CampaignResultService
    ],
})





export class ManagerHomeModule { }