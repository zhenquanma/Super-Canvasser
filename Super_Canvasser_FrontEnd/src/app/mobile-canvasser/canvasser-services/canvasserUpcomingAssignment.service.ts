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
export class UpcomingAssignmentService {
  public base="http://localhost:5438/canvasser"
  public canvasserLoadUpcomingAssignmentListURL =this.base+"/LoadUpcomingAssignmentList";
  public canvasserLoadUpcomingAssignmentToMapURL=this.base+"/LoadUpcomingAssignmentToMap"



  constructor(public http:Http,
              private httpClient:HttpClient) { }




    public canvasser_LoadUpcomingAssignmentList():Observable<any[]>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = {
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };
 
      let url = this.canvasserLoadUpcomingAssignmentListURL;
 
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


 

    public canvasser_LoadUpcomingAssignmentToMap():Observable<any[]>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = {
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };
      
      let url = this.canvasserLoadUpcomingAssignmentToMapURL;
      
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


}