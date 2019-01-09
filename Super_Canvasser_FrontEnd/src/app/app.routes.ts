import {RouterModule} from '@angular/router';
import { AppComponent } from './app.component';

//import { ManagerHomeComponent } from './web-manager/manager-home/manager-home.component';
//import { CanvasserHomeComponent } from './mobile-canvasser/canvasser-home/canvasser-home.component';
import { LoginUnitComponent } from './loginShell/login-unit/login-unit.component';
import { HomepageComponent } from './home/homepage/homepage.component';
import {AuthGuard, AuthGuard_Admin, AuthGuard_Manager, AuthGuard_Canvasser} from './auth-guard';

export const MyRoutes=[
{
    path:'',
    component:HomepageComponent
 

},

    {
    path:"AdminHome",
    //canActivate: [AuthGuard_Admin],
    loadChildren:'./web-admin/admin.module#AdminHomeModule'
},
    {
        path:"ManagerHome",
       // canActivate: [AuthGuard_Manager],
        loadChildren:'./web-manager/manager.module#ManagerHomeModule'
        //component:ManagerHomeComponent
    },
    {
        path:"CanvasserHome",
       // canActivate: [AuthGuard_Canvasser],
        loadChildren:'./mobile-canvasser/canvasser.module#CanvasserHomeModule'
        //component:CanvasserHomeComponent
    }
    ,

    {
		path:'Login',
		component:LoginUnitComponent
	}
];