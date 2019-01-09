import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { Http, Headers, Response } from '@angular/http';



import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';



@Injectable()
export class LoginService {

  public userLoginURL="http://localhost:5438/login"
  public subject: Subject<any> = new Subject<any>();
  
  constructor(public http:Http){}

  public get currentUser():Observable<any>{
      return this.subject.asObservable();
  }




 



  public login(user){
    console.log("login.service=>login")
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
    let json={
      username:user.userName,
      password:user.password
    }
    let url=this.userLoginURL;
    return this.http
            .post(url,JSON.stringify(json),httpOptions)
            .map((response: Response) => {
              console.log("login.service=>login response")

              let result=response.json()
                  if (result.status=="OK"){
                        if (result.msg!="No data found"){
                            let user=response.json().data;
                            let token=response.headers.get("authorization")
                

                              if(user && token){
                                localStorage.setItem("currentUser",JSON.stringify(user));
                                localStorage.setItem("Authorization",token)
                                localStorage.setItem("Login_UserID",user.id)
                                
                                this.subject.next(Object.assign({},user));
                              }
                              return response;
                        }else{
                          alert("No data found")
                        }
                  }else{
                    alert("Mistake=>"+result.msg)
                  }



            })
            .subscribe(
                data => {
                    //console.log("login success>"+data);
                },
                error => {
                    console.error(error);
                }
            );
  }

  public logout():void{
   
    console.log("login.service=>logout")
    localStorage.removeItem("currentUser");
    localStorage.removeItem("Authorization")
 
  
    localStorage.removeItem("Login_UserID");


    localStorage.removeItem("Manager_CampaignID");
    localStorage.removeItem("Manager_AssignmentID");
    localStorage.removeItem("Manager_StartDate")
    localStorage.removeItem("Manager_EndDate")



    localStorage.removeItem("Canvasser_CampaignID");
    localStorage.removeItem("Canvasser_AssignmentID");
    localStorage.removeItem("Canvasser_LocationID");
    localStorage.removeItem("Canvasser_LocationDetail")

    localStorage.removeItem("Admin_UserID")


    this.subject.next(Object.assign({}));
  }
}