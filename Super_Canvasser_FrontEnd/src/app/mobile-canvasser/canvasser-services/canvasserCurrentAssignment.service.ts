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
export class CurrentAssignmentService {

  public base="http://localhost:5438/canvasser"
  public canvasserLoadCurrentAssignmentInfoURL = this.base+"/LoadCurrentAssignmentInfo";


  public canvasserLoadCurrentLocationURL=this.base+"/LoadCurrentLocation";
  public canvasserLoadNextLocationURL=this.base+"/LoadNextLocation";

  public canvasserLoadCurrentAssignmentLocationListURL=this.base+"/LoadCurrentAssignmentLocationList";//include map


  //special location page   /CanvasserLocationInfo
  public canvasserLoadLocationPreInfoURL=this.base+"/LoadLocationPreInfo";
  public canvasserPostLocationResultURL=this.base+"/PostLocationResult"



  constructor(public http:Http,
              private httpClient:HttpClient) { }





    //need current assignment info, so use post
    public canvasser_LoadCurrentAssignmentInfo():Observable<any>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = { 
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };

      let url = this.canvasserLoadCurrentAssignmentInfoURL;

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



    public canvasser_LoadCurrentLocation(assignmentID):Observable<any>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = {
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };

      let url = this.canvasserLoadCurrentLocationURL;
      let json={assignmentID:assignmentID}
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




    public canvasser_LoadNextLocation(assignmentID):Observable<any>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = {
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };

      let url = this.canvasserLoadNextLocationURL;
      let json={assignmentID:assignmentID}

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


    public canvasser_LoadCurrentAssignmentLocationList(assignmentID):Observable<any[]>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = {
      
         
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };

      let url = this.canvasserLoadCurrentAssignmentLocationListURL;
      let json={assignmentID:assignmentID}
    
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











    public canvasserLoadLocationPreInfo(campaignID:string,assignmentID:string,locationID:string):Observable<any>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = {
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };

      let url = this.canvasserLoadLocationPreInfoURL;

      let json={
        campaignID:campaignID,
        assignmentID:assignmentID,
        locationID:locationID
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



    public canvasserPostLocationResult(postDetail):Observable<any>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = {
      
         
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };

      let url = this.canvasserPostLocationResultURL;


      let json={

        assignmentID:postDetail.AssignmentID,
        locationID:postDetail.LocationID,
  
        rating:postDetail.Rating,
        speakAnswer:postDetail.SpeakAnswer,
        note:postDetail.Note,
        questions:postDetail.Questions   //["",""]
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
