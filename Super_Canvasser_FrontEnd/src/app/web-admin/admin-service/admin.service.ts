import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions,URLSearchParams } from '@angular/http';
//import { userInfoUnit} from '../models/user-model';

//import { Observable } from 'rxjs/Rx';
import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';




@Injectable()
export class AdminService{
  public base="http://localhost:5438/admin"
  public admin_GetUserListURL=this.base+"/GetUserList";

  	
	public admin_PostUserURL=this.base+"/PostUser";
  public admin_DeleteUserURL=this.base+"/DeleteUser";
  
	//public getUserInfoURL = "http://localhost:5438/getUserInfo"



  public admin_EditUserURL=this.base+"/EditUser";
  public admin_EditUserLoadWindowURL=this.base+"/EditUserLoadWindow";

//admin setting
  public admin_GetMaxDurationURL=this.base+"/GetMaxDuration";
  public admin_EditMaxDurationURL=this.base+"/EditMaxDuration";
  public admin_GetAveSpeedURL=this.base+"/GetAveSpeed";
  public admin_EditAveSpeedURL=this.base+"/EditAveSpeed";

	constructor(public http:Http,
              private httpClient:HttpClient) { }





  public admin_GetUserList(): Observable<any[]>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
  	console.log("service get user infor")
  	let url = this.admin_GetUserListURL;

  	return this.http.get(url,httpOptions)
              .map((res:Response)=>{
              let result=res.json();
              if (result.status=="OK"){
                if (result.msg!="No data found"){
                  console.log(result);
                  return result.data;
                }else{
                  alert("No data found")
                }
              }else{
                  alert("Mistake=>"+result.msg)
              }

          })
          .catch((error:any)=>Observable.throw(error || 'Server error'));
  }


  // public getUserInfo(UserId:string):Observable<any>{

  // 	let url=this.getUserInfoURL;
  // 	let json={UserID: UserId}
  // 	return this.http.post(url,JSON.stringify(json),httpOptions)
  //             .map((res:Response)=>{
  //             let result=res.json();
  //             console.log(result);
  //             return result.data;
  //         })
  //         .catch((error:any)=>Observable.throw(error || 'Server error'));
  // }



  public admin_EditUser( UserInfoWindow: any):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
  	let url=this.admin_EditUserURL;
  	let json={
  	userID: UserInfoWindow.UserID,
    firstName:UserInfoWindow.FirstName,
    password:UserInfoWindow.Password,
		lastName: UserInfoWindow.LastName,
		userName: UserInfoWindow.UserName,
		role:UserInfoWindow.Role,
  	}
  	return this.http.post(url,JSON.stringify(json),httpOptions)
              .map((res:Response)=>{
              let result=res.json();
              if (result.status=="OK"){
                if (result.msg!="No data found"){
                    return result.data;
                }else{
                  alert("No data found")
                }
              }else{
                  alert("Mistake=>"+result.msg)
              }

          })
          .catch((error:any)=>Observable.throw(error || 'Server error'));


  }




  public admin_EditUserLoadWindow(userId){
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
    let url=this.admin_EditUserLoadWindowURL;
  	let json={
        userId:userId
  	}
  	return this.http.post(url,JSON.stringify(json),httpOptions)
              .map((res:Response)=>{
              let result=res.json();
              if (result.status=="OK"){
                if (result.msg!="No data found"){
                     return result.data;
                }else{
                  alert("No data found")
                }
              }else{
                  alert("Mistake=>"+result.msg)
              }

          })
          .catch((error:any)=>Observable.throw(error || 'Server error'));
  }




  

  public admin_PostUser(UserInfoWindow:any):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
  	let url=this.admin_PostUserURL;
  	let json={
  	
		firstName:UserInfoWindow.FirstName,
		lastName: UserInfoWindow.LastName,
    userName: UserInfoWindow.UserName,
    password:UserInfoWindow.Password,
		role:UserInfoWindow.Role,
  	}

  	 return this.http.post(url,JSON.stringify(json),httpOptions)
              .map((res:Response)=>{
              let result=res.json();
              if (result.status=="OK"){
                if (result.msg!="No data found"){
                    console.log(result);
                    return result.data;
                }else{
                  alert("No data found")
                }
              }else{
                  alert("Mistake=>"+result.msg)
              }

          })
          .catch((error:any)=>Observable.throw(error || 'Server error'));
  }


  public admin_DeleteUser(UserId):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
  	let url=this.admin_DeleteUserURL;
  	let json={
  		userID:UserId
  	}

  	return this.http.post(url,JSON.stringify(json),httpOptions)
              .map((res:Response)=>{
              let result=res.json();
              if (result.status=="OK"){
                if (result.msg!="No data found"){
                      console.log(result);
                      return result.data;
                }else{
                  alert("No data found")
                }
              }else{
                  alert("Mistake=>"+result.msg)
              }

          })
          .catch((error:any)=>Observable.throw(error || 'Server error'));

  }








//admin setting  
public admin_GetMaxDuration(){
  let token=localStorage.getItem("Authorization")
  let  httpOptions = {
      headers: new Headers({
        'Content-Type':  'application/json',
        'Authorization': token
      })
    };

  let url=this.admin_GetMaxDurationURL;

  return this.http.get(url,httpOptions)
  .map((res:Response)=>{
  let result=res.json();
  if (result.status=="OK"){
    if (result.msg!="No data found"){
      console.log(result);
      return result.data;
    }else{
      alert("No data found")
    }
  }else{
      alert("Mistake=>"+result.msg)
  }
})
.catch((error:any)=>Observable.throw(error || 'Server error'));


}




public admin_GetAveSpeed(){
  let token=localStorage.getItem("Authorization")
  let  httpOptions = {
  
     
      headers: new Headers({
        'Content-Type':  'application/json',
        'Authorization': token
      })
    };
        let url=this.admin_GetAveSpeedURL;

        return this.http.get(url,httpOptions)
        .map((res:Response)=>{
        let result=res.json();
        if (result.status=="OK"){
          if (result.msg!="No data found"){
                console.log(result);
                return result.data;
            }else{
              alert("No data found")
            }
          }else{
              alert("Mistake")
          }

      })
      .catch((error:any)=>Observable.throw(error || 'Server error'));


}




  public admin_EditMaxDuration(maxDuration){
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
    let url=this.admin_EditMaxDurationURL;
    let json={
      maxDuration:maxDuration
    }

    return this.http.post(url,JSON.stringify(json),httpOptions)
    .map((res:Response)=>{
    let result=res.json();
    if (result.status=="OK"){
      if (result.msg!="No data found"){
            console.log(result);
            return result.data;
      }else{
        alert("No data found")
      }
    }else{
        alert("Mistake=>"+result.msg)
    }

})
.catch((error:any)=>Observable.throw(error || 'Server error'));


  }



  public admin_EditAveSpeed(aveSpeed){
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
    let url=this.admin_EditAveSpeedURL;
    let json={
      aveSpeed:aveSpeed
    }

    return this.http.post(url,JSON.stringify(json),httpOptions)
    .map((res:Response)=>{
    let result=res.json();
    if (result.status=="OK"){
      if (result.msg!="No data found"){
            console.log(result);
            return result.data;
      }else{
        alert("No data found")
      }
    }else{
        alert("Mistake=>"+result.msg)
    }

})
.catch((error:any)=>Observable.throw(error || 'Server error'));


  }





}