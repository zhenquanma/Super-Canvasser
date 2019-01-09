
import { Component, OnInit,Input } from '@angular/core';
import { ActivatedRoute, Router, ActivatedRouteSnapshot, RouterState, RouterStateSnapshot } from '@angular/router';
import { LoginService } from '../login-services/login.service';
import { Observable } from 'rxjs/Observable';



@Component({
  selector: 'app-login-unit',
  templateUrl: './login-unit.component.html',
  styleUrls: ['./login-unit.component.css']
})
export class LoginUnitComponent implements OnInit {
  public user={}
  public error : Error;
  constructor(        
        public router: Router,
        public activatedRoute: ActivatedRoute,
        public LoginService: LoginService) 
            {   //console.log(this.LoginService);
            }

  ngOnInit() {


    let activatedRouteSnapshot:ActivatedRouteSnapshot=this.activatedRoute.snapshot;
    let routerState: RouterState = this.router.routerState;
    let routerStateSnapshot: RouterStateSnapshot = routerState.snapshot;


  }

  public doLogin():void{
        //console.log(this.user);
        this.LoginService.login(this.user);
  }

  public doLogout():void{
        this.LoginService.logout();
        this.router.navigateByUrl("home");
  }



}
