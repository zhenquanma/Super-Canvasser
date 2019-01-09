import { Component, OnInit } from '@angular/core';



import { ChangeDetectionStrategy } from '@angular/core';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';

import { colors } from '../demo-utils/colors';

import { Subject } from 'rxjs';

import {CalendarService} from '../canvasser-services/canvasserCalendar.service';

@Component({
  selector: 'app-canvasser-calendar',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './canvasser-calendar.component.html',
  styleUrls: ['./canvasser-calendar.component.css']
})
export class CanvasserCalendarComponent implements OnInit {

  constructor(public CalendarService:CalendarService) { }

  ngOnInit() {
    this.loadData()
  }


  view: string = 'month';
  viewDate: Date = new Date();
  refresh: Subject<any> = new Subject();
  events: CalendarEvent[] = [];
  availableDays:Date[]=[];
  clickedDate: Date;
  

public loadData(){
          console.log("canvasser-calendar=>loadEvent")
          return this.CalendarService.canvasser_LoadCalendarEventList().subscribe(
            res=>{
              

              length=res.length
              for (let each of res){
                    this.events.push(      
                      {
                        title: 'available',
                        color: colors.yellow,
                        start: new Date(each),
                      }  
                  )
                this.availableDays.push(each.toString())
              }
              this.refreshView()
            },
            
            error => {console.log(error)},
            );
}


  public refreshView(): void {

        this.refresh.next();
  }


  public setDay(date){
  
        console.log("canvasser-calendar=>setday")
        if (this.availableDays.indexOf(date.toDateString() )==-1){
    
          this.events.push(      
                {
                  title: 'available',
                  color: colors.yellow,
                  start: new Date(date),
                }  
            )
          this.availableDays.push(date.toDateString())
          this.CalendarService.canvasser_CalendarAddEvent(date.toDateString()).subscribe(res=>{})

        }else{
        
          let index=this.availableDays.indexOf(date.toDateString());
          this.availableDays.splice(index,1);
          this.events.splice(index,1);

          this.CalendarService.canvasser_CalendarRemoveEvent(date.toDateString()).subscribe(res=>{})
        }
        this.refreshView()
  }



  
  public loadDay(k){
        k.forEach((val, idx, array) => {
          // val: current value
          // idx：current index
          // array: Array

          this.insertToList(val)
      });
  }
  

  public insertToList(dataUnit){
    this.events.push(      
          {
            title: 'available',
            color: colors.yellow,
            start: new Date(dataUnit),
          }   
      )
    this.availableDays.push(dataUnit.toDateString())

  }

}


