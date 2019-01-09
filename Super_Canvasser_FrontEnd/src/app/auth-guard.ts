import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import { LoginService } from './loginShell/login-services/login.service';

@Injectable()
export class AuthGuard implements CanActivate {
  	constructor(
  		private router: Router,
  		public LoginService: LoginService) {

  	}
  
  	canActivate(route: ActivatedRouteSnapshot,state: RouterStateSnapshot): boolean {
  		//这里可以调用真实的服务进行验证
  			// this.LoginService.currentUser
		    //  .subscribe(
			//  	data => {
					
			//  	},
			//  	error => console.error(error)
			//  );
    	return true;
  	}
}

@Injectable()
export class AuthGuard_Admin implements CanActivate {
  	constructor(
  		private router: Router,
  		public LoginService: LoginService) {

  	}
  
  	canActivate(route: ActivatedRouteSnapshot,state: RouterStateSnapshot): boolean {
  		//这里可以调用真实的服务进行验证
  			this.LoginService.currentUser
		     .subscribe(
			 	data => {
                     if(data.role=="Admin"){
                         return true;
                     }else{
                         return false;
                     }
					
			 	},
			 	error => console.error(error)
			 );
    	return false;
  	}
}


@Injectable()
export class AuthGuard_Manager implements CanActivate {
  	constructor(
  		private router: Router,
  		public LoginService: LoginService) {

  	}
  
  	canActivate(route: ActivatedRouteSnapshot,state: RouterStateSnapshot):boolean {
  		//这里可以调用真实的服务进行验证
  			this.LoginService.currentUser
		     .subscribe(
			 	data => {
                    if(data.role=="Manager"){
                        return true;
                    }else{
                        return false;
                    }
			 	},
			 	error => console.error(error)
			 );
    	return false;
  	}
}



@Injectable()
export class AuthGuard_Canvasser implements CanActivate {
  	constructor(
  		private router: Router,
  		public LoginService: LoginService) {

  	}
  
  	canActivate(route: ActivatedRouteSnapshot,state: RouterStateSnapshot): boolean {
  		//这里可以调用真实的服务进行验证
  			this.LoginService.currentUser
		     .subscribe(
			 	data => {
                    if(data.role=="Canvasser"){
                        return true;
                    }else{
                        return false;
                    }
					
			 	},
			 	error => console.error(error)
			 );
    	return false;
  	}
}