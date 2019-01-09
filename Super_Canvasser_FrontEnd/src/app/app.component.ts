//import { Component } from '@angular/core';
import { Component, HostListener, ElementRef, Renderer, ViewContainerRef } from '@angular/core';
import { ActivatedRoute, Router, ActivatedRouteSnapshot, RouterState, RouterStateSnapshot } from '@angular/router';

//import { User } from './loginShell/models/user-model';
import { LoginService } from './loginShell/login-services/login.service';
//import { Message } from 'primeng/primeng';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
	//public msgs: Message[] = [];
	public currentUser={}
	private globalClickCallbackFn: Function;
	private loginSuccessCallbackFn: Function;

	constructor(
		public elementRef: ElementRef,
		public renderer: Renderer,
		public router: Router,
		public activatedRoute: ActivatedRoute,

		public loginService: LoginService,
		//public userRegisterService: UserRegisterService
	) {

	}

	ngOnInit() {
		console.log(this.currentUser)
		this.globalClickCallbackFn = this.renderer.listen(this.elementRef.nativeElement, 'click', (event: any) => {
			//console.log("listen global>" + event);
		});

		this.currentUser = JSON.parse(localStorage.getItem("currentUser"));
		console.log(this.currentUser)
		console.log(localStorage)

		this.loginService.currentUser
			//.merge(this.userRegisterService.currentUser)
			.subscribe(
			data => {
				this.currentUser = data;
				console.log(this.currentUser)
				let activatedRouteSnapshot: ActivatedRouteSnapshot = this.activatedRoute.snapshot;
				let routerState: RouterState = this.router.routerState;
				let routerStateSnapshot: RouterStateSnapshot = routerState.snapshot;

				console.log(activatedRouteSnapshot);
				console.log(routerState);
				console.log(routerStateSnapshot);

				//如果是从/login这个URL进行的登录，跳转到首页，否则什么都不做
				if (routerStateSnapshot.url.indexOf("/Login") != -1) {
					this.router.navigateByUrl("");
				}
			},
			error => console.error(error)
			);


	}

	ngOnDestroy() {
		if (this.globalClickCallbackFn) {
			this.globalClickCallbackFn();
		}
	}

	toggle(button: any) {
		console.log(button);
	}

	public doLogout(): void {
		this.loginService.logout();
		this.router.navigateByUrl("");
	}
}
