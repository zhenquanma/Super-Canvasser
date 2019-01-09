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
export class CampaignService {
  public base="http://localhost:5438/manager"

  public manager_GetCampaignListURL = this.base+"/GetCampaignList";//changer in testbackend too


  public manager_GetCampaignInfoURL = this.base+"/GetCampaignInfo";
  public manager_EditCampaignWindowURL = this.base+"/EditCampaignWindow";


  public manager_PostCampaignURL=this.base+"/PostCampaign";
  public manager_DeleteCampaignURL=this.base+"/DeleteCampaign";
  public manager_EditCampaignURL=this.base+"/EditCampaign";

  public manager_CreateCampaign_LoadCanvasserAccordingToDateURL=this.base+"/CreateCampaign_LoadCanvasserAccordingToDate";
  public manager_CreateCampaign_LoadManagerURL=this.base+"/CreateCampaign_LoadManager";


  public manager_EditCampaign_LoadCanvasserAccordingToDateURL=this.base+"/EditCampaign_LoadCanvasserAccordingToDate";

  public manager_GetCampaignLocationsListURL=this.base+"/GetCampaignLocationsList";

  constructor(public http:Http,
              private httpClient:HttpClient) { }





  public manager_GetCampaignList():Observable<any[]>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
    let url = this.manager_GetCampaignListURL;

    return this.http
               .get(url,httpOptions)
               .map((res:Response) => {
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
               .catch((error:any) => Observable.throw(error || 'Server error'));
  }




  public manager_GetCampaignInfo(CampaignID:String):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_GetCampaignInfoURL;
    let json={campaignID:CampaignID}
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




  public manager_EditCampaignWindow(CampaignID:String):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_EditCampaignWindowURL;
    let json={campaignID:CampaignID}
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





  public manager_PostCampaign(campaignWindow:any):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_PostCampaignURL;


    let json={

      campaignName:campaignWindow.CampaignName,
      startDate:campaignWindow.StartDate,
      endDate:campaignWindow.EndDate,
      managers:campaignWindow.Managers,
      canvassers:campaignWindow.Canvassers,
      talkingPoints:campaignWindow.TalkingPoints,
      questions:campaignWindow.Questions,
      locations:campaignWindow.Locations,
      visitDuration:campaignWindow.VisitDuration

      //other later to input
     };
    return this.http.post(url,JSON.stringify(json),httpOptions)
            .map((res:Response)=>{
              let result=res.json();
              if (result.status=="OK"){
                if (result.msg!="No data found"){
              console.log('postCampaign->');
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

  public manager_DeleteCampaign(CampaignID:String):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };


    let url=this.manager_DeleteCampaignURL;
    let json={campaignID:CampaignID};

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




  public manager_EditCampaign(campaignWindow:any):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };


    let url=this.manager_EditCampaignURL;
    let json={
      campaignID:campaignWindow.CampaignID,
      campaignName:campaignWindow.CampaignName,
      startDate:campaignWindow.StartDate,
      endDate:campaignWindow.EndDate,
      managers:campaignWindow.Managers,
      canvassers:campaignWindow.Canvassers,
      talkingPoints:campaignWindow.TalkingPoints,
      questions:campaignWindow.Questions,
      locations:campaignWindow.Locations,
      visitDuration:campaignWindow.VisitDuration

     };


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






  public manager_CreateCampaign_LoadCanvasserAccordingToDate(StartDate,EndDate):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };


    let url=this.manager_CreateCampaign_LoadCanvasserAccordingToDateURL;


    let json={
      
      startDate:StartDate,
      endDate:EndDate,

     };
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



  public manager_EditCampaign_LoadCanvasserAccordingToDate(StartDate,EndDate):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };


    let url=this.manager_EditCampaign_LoadCanvasserAccordingToDateURL;


    let json={

      startDate:StartDate,
      endDate:EndDate,

     };
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





  public manager_CreateCampaign_LoadManager():Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };
    let url=this.manager_CreateCampaign_LoadManagerURL;
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





  public manager_GetCampaignLocationsList(campaignID):Observable<any>{
    let token=localStorage.getItem("Authorization")
    let  httpOptions = {
    
       
        headers: new Headers({
          'Content-Type':  'application/json',
          'Authorization': token
        })
      };

    let url=this.manager_GetCampaignLocationsListURL;
    let json={campaignID:campaignID}
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










  public getLatLngBaseonLocation(){
    //location,number,street,unit,city,state
    //format: NUMBER, STREET, UNIT, CITY, STATE, ZIP.  Example: 40, Piedmont Drive, Apartment 16B, Brookhaven, NY, 11776  the system should allow at least 100 addresses to be uploaded at a time.

  let kk="2143 coney islad ave, brooklyn, new york"
   // let url="https://nominatim.openstreetmap.org/?format=json&addressdetails=1&q=2143+coneyislandave+1r+brooklyn+ny&format=json&limit=1";
  let url="https://maps.googleapis.com/maps/api/geocode/json?address="+kk+"&key=AIzaSyCSBQ5PQYQZwAxqE08kxZlQKR1yuwu7Xnc"


  return this.http.get(url)
  .map((res:Response)=>{
    let result=res.json();
    console.log("map")
    console.log(result);
    return result;
  })
  .catch((error:any)=>Observable.throw(error || 'Server error'));
}

  public getLocationBaseonLatLng(){
    //let url= "https://nominatim.openstreetmap.org/reverse?format=json&lat="+lat+"&lon="+lng;
    let lat= 40.6048364;
    let lng=-73.96122439999999;
    let url="https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+"+"+lng+"&key=AIzaSyCSBQ5PQYQZwAxqE08kxZlQKR1yuwu7Xnc"

    return this.http.get(url)
    .map((res:Response)=>{
      let result=res.json();
      console.log("map2")
      console.log(result);
      return result;
    })
    .catch((error:any)=>Observable.throw(error || 'Server error'));
  }


}
