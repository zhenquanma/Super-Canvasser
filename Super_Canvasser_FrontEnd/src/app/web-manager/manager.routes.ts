import { RouterModule } from '@angular/router';

import { ManagerHomeComponent } from './manager-home/manager-home.component';
import { ManagerCampaignListComponent } from './manager-campaign-list/manager-campaign-list.component';

import { ManagerCreateCampaignComponent } from './manager-create-campaign/manager-create-campaign.component';
import { ManagerCreateAssignmentComponent } from './manager-create-assignment/manager-create-assignment.component';
import { ManagerEditCampaignComponent } from './manager-edit-campaign/manager-edit-campaign.component';
import { ManagerEditAssignmentComponent } from './manager-edit-assignment/manager-edit-assignment.component';
import { ManagerViewCampaignComponent } from './manager-view-campaign/manager-view-campaign.component';

import { ManagerCampaignInfoComponent } from './manager-campaign-info/manager-campaign-info.component';
import { ManagerViewAssignmentComponent } from './manager-view-assignment/manager-view-assignment.component';

import { ManagerCampaignResultComponent } from './manager-campaign-result/manager-campaign-result.component';



export const MyManagerRoutes=[
	{
        path:'',
        component:ManagerHomeComponent,
        children:[{
            path:'ManagerCampaignList',
            component: ManagerCampaignListComponent

        },

        {
            path:"ManagerCreateCampaign",
            component:ManagerCreateCampaignComponent
        },
        {
            path:"ManagerCreateAssignment",
            component:ManagerCreateAssignmentComponent
        },
        {
            path:"ManagerEditCampaign",
            component:ManagerEditCampaignComponent
        },
        {
            path:"ManagerEditAssignment",
            component:ManagerEditAssignmentComponent
        },
        {
            path:"ManagerViewCampaign",
            component:ManagerViewCampaignComponent
        },

        {
            path:"ManagerViewAssignment",
            component:ManagerViewAssignmentComponent
        },

        {
            path:"ManagerCampaignInfo",
            component: ManagerCampaignInfoComponent
        },
        {
            path:"ManagerCampaignResult",
            component: ManagerCampaignResultComponent
        },

    ]
    }
];