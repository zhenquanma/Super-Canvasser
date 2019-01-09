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
export class CalendarService {
  public base="http://localhost:5438/canvasser"
  public canvasserLoadCalendarEventListURL = this.base+"/LoadCalendarEventList";

  public canvasserCalendarAddEventURL = this.base+"/CalendarAddEvent";
  public canvasserCalendarRemoveEventURL = this.base+"/CalendarRemoveEvent";
 
  constructor(public http:Http,
              private httpClient:HttpClient) { }






    //backend can get the user from token
    public canvasser_LoadCalendarEventList():Observable<any[]>{

      let token=localStorage.getItem("Authorization")


      let  httpOptions = {   
            headers: new Headers({
              'Content-Type':  'application/json',
              'Authorization': token
            })
          };

      let url = this.canvasserLoadCalendarEventListURL;

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



    //backend can get the user from token
    public canvasser_CalendarAddEvent(date):Observable<any>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = {   
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };
      let url = this.canvasserCalendarAddEventURL;
      let json={
        date:date
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



    //backend can get the user from token
    public canvasser_CalendarRemoveEvent(date):Observable<any>{
      let token=localStorage.getItem("Authorization")
      let  httpOptions = {   
          headers: new Headers({
            'Content-Type':  'application/json',
            'Authorization': token
          })
        };

      let url = this.canvasserCalendarRemoveEventURL;
      let json={
        date:date
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