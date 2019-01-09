import { NgModule } from '@angular/core';

import { RouterModule } from '@angular/router';
import { AdminHomeComponent }   from './admin-home/admin-home.component';


import {MyAdminRoutes} from './admin.routes';
import { AdminSettingListComponent } from './admin-setting-list/admin-setting-list.component';
import { AdminUserListComponent } from './admin-user-list/admin-user-list.component';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { AdminCreateUserComponent } from './admin-create-user/admin-create-user.component';
import { AdminEditUserComponent } from './admin-edit-user/admin-edit-user.component';
import {CommonModule} from '@angular/common';


import { HttpModule,JsonpModule ,Http} from '@angular/http';
import {HttpClientModule} from "@angular/common/http";
import { FormsModule } from '@angular/forms';
import {AdminService } from './admin-service/admin.service';
//import { AdminViewUserlistComponent } from './admin-view-userlist/admin-view-userlist.component';


@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        RouterModule.forChild(MyAdminRoutes),
        NgbModule,
        HttpModule,
        JsonpModule,
        FormsModule,
        HttpClientModule 
    ],
    exports: [],
    declarations: [
        AdminHomeComponent,
        AdminSettingListComponent,
        AdminUserListComponent,
        AdminCreateUserComponent,
        AdminEditUserComponent,
        //AdminViewUserlistComponent,

    ],
    providers: [
    AdminService ],
})
export class AdminHomeModule { }