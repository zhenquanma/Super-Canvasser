import { RouterModule } from '@angular/router';
import { AdminHomeComponent } from './admin-home/admin-home.component';


import { AdminSettingListComponent } from './admin-setting-list/admin-setting-list.component';
import { AdminUserListComponent } from './admin-user-list/admin-user-list.component';
import  {AdminCreateUserComponent} from './admin-create-user/admin-create-user.component';
import  {AdminEditUserComponent} from './admin-edit-user/admin-edit-user.component';




export const MyAdminRoutes=[
	{
        path:'',
        component:AdminHomeComponent,
        children:[{
            path:'AdminUserList',
            component:AdminUserListComponent
         },
         {
         	path:'AdminSettingList',
         	component:AdminSettingListComponent
         },
         {
             path:'AdminCreateUser',
            component:AdminCreateUserComponent

         },{
             path:'AdminEditUser',
             component:AdminEditUserComponent
         }
         
         ]
    }
];