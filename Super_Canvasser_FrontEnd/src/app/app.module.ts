import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { HttpModule,JsonpModule ,Http} from '@angular/http';

import { AppComponent } from './app.component';

import {MyRoutes} from './app.routes';
import { LoginUnitComponent } from './loginShell/login-unit/login-unit.component';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import {LoginService} from './loginShell/login-services/login.service';
import { HomepageComponent } from './home/homepage/homepage.component';
import {AuthGuard, AuthGuard_Admin, AuthGuard_Manager, AuthGuard_Canvasser} from './auth-guard';
//import { ManagerHomeComponent } from './web-manager/manager-home/manager-home.component';
//import { CanvasserHomeComponent } from './mobile-canvasser/canvasser-home/canvasser-home.component';
//import { AdminHomeComponent } from './web-admin/admin-home/admin-home.component';




//import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


//import {NgbModule} from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
    AppComponent,
    LoginUnitComponent,
    HomepageComponent,
   // ManagerHomeComponent,
   // CanvasserHomeComponent,
   //AdminHomeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,                              
    ReactiveFormsModule,
    HttpModule,
    RouterModule,
    RouterModule.forRoot(MyRoutes),
    BrowserAnimationsModule,
   // NgbModule
  ],
  providers: [LoginService,AuthGuard, AuthGuard_Admin, AuthGuard_Manager, AuthGuard_Canvasser],
  bootstrap: [AppComponent]
})
export class AppModule { }
