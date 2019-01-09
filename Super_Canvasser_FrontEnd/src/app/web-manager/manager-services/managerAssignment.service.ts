import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions,URLSearchParams } from '@angular/http';

import { Observable, Subject, asapScheduler, pipe, of, from, interval, merge, fromEvent } from 'rxjs';
import { map, filter, scan } from 'rxjs/operators';
import { webSocket } from 'rxjs/webSocket';
import { ajax } from 'rxjs/ajax';
import { TestScheduler } from 'rxjs/testing';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';





import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';



@Injectable()
export class AssignmentService {
  public base="http://localhost:5438/manager"
  public manager_GetAssignmentListURL = this.base+"/GetAssignmentList";//change website
  public manager_GetAssignmentInfoURL=this.base+"/GetAssignmentInfo";
  public manager_EditAssignmentWindowURL=this.base+"/EditAssignmentWindow";


  public manager_PostAssignmentURL=this.base+"/PostAssignment";
  public manager_DeleteAssignmentURL=this.base+"/DeleteAssignment";
  public manager_EditAssignmentURL=this.base+"/EditAssignment";



  public manager_CreateAssignment_LoadCanvasserAccordingToDateURL=this.base+"/CreateAssignment_LoadCanvasserAccordingToDate";
  public manager_CreateAssignment_LoadLocationsURL=this.base+"/CreateAssignment_LoadLocations";
  public manager_EditAssignment_LoadCanvasserAccordingToDateURL=this.base+"/EditAssignment_LoadCanvasserAccordingToDate";

 public manager_GetAssignmentAllLocationURL=this.base+"/GetAssignmentAllLocation";

 constructor(public http:Http,
              private httpClient:HttpClient) { }




  public manager_GetAssignmentList(campaignId:string):Observable<any[]>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url = this.manager_GetAssignmentListURL;

    let json={campaignID:campaignId}
    return this.http.post(url,JSON.stringify(json),httpOptions)
              .map((res:Response)=>{
              let result=res.json();
              // if (result.status=="Error"){
              //   alert("mistake")
              // }
              if (result.status=="OK"){
                if (result.msg!="No data found"){
              return result.data;
                }
              }
          })
          .catch((error:any)=>Observable.throw(error || 'Server error'));
  }



  public manager_GetAssignmentInfo(assignmentId:String):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_GetAssignmentInfoURL;
    let json={assignmentID:assignmentId}
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



  public manager_EditAssignmentWindow(assignmentId:String):Observable<any>{
    console.log("dfdfdfdfdfd")
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_EditAssignmentWindowURL;
    let json={assignmentID:assignmentId}
    console.log(url)
    return this.http.post(url,JSON.stringify(json),httpOptions)
              .map((res:Response)=>{
              let result=res.json();
              console.log(result)
              if (result.status=="OK"){
                if (result.msg!="No data found"){
                  console.log("1233334343")
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


  public manager_PostAssignment(campaignID,assignmentWindow:any):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_PostAssignmentURL;
    let json={
      campaignID:campaignID,
      assignmentDate:assignmentWindow.AssignmentDate,
      locations:assignmentWindow.Locations,
      canvasser:assignmentWindow.Canvasser,
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

  public manager_DeleteAssignment(assignmentID:String):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };


    let url=this.manager_DeleteAssignmentURL;
    let json={
      assignmentID:assignmentID
    };
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


  public manager_EditAssignment(assignmentWindow:any):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };


    let url=this.manager_EditAssignmentURL;
    let json={
      assignmentID:assignmentWindow.AssignmentID,
      assignmentDate:assignmentWindow.AssignmentDate,
      locations:assignmentWindow.Locations,
      canvasser:assignmentWindow.Canvasser,
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




  public manager_CreateAssignment_LoadCanvasserAccordingToDate(campaignID,date):Observable<any[]>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_CreateAssignment_LoadCanvasserAccordingToDateURL;
    let json={
      campaignID:campaignID,
      date:date}
    return this.http
               .post(url,JSON.stringify(json),httpOptions)
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
    .catch((error:any)=>Observable.throw(error||'Server error'));

  }



  public manager_EditAssignment_LoadCanvasserAccordingToDate(campaignID,assignmentID,date):Observable<any[]>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_EditAssignment_LoadCanvasserAccordingToDateURL;
    let json={campaignID:campaignID,assignmentID:assignmentID,date:date}
    return this.http
               .post(url,JSON.stringify(json),httpOptions)
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
    .catch((error:any)=>Observable.throw(error||'Server error'));

  }


  public manager_CreateAssignment_LoadLocations(campaignID:string):Observable<any[]>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };


    let url=this.manager_CreateAssignment_LoadLocationsURL;

    let json={campaignID:campaignID}
    return this.http
               .post(url,JSON.stringify(json),httpOptions)
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
    .catch((error:any)=>Observable.throw(error||'Server error'));

  }



  public manager_GetAssignmentAllLocation(assignmentID):Observable<any[]>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_GetAssignmentAllLocationURL;
    let json={assignmentID:assignmentID}

    return this.http
               .post(url,JSON.stringify(json),httpOptions)
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
    .catch((error:any)=>Observable.throw(error||'Server error'));

  }




}
