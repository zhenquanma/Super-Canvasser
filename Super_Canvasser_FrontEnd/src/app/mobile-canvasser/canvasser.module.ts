import { NgModule } from '@angular/core';

import { RouterModule } from '@angular/router';
import { CanvasserHomeComponent }   from './canvasser-home/canvasser-home.component';


import {MyCanvasserRoutes} from './canvasser.routes';
import { CanvasserCalendarComponent } from './canvasser-calendar/canvasser-calendar.component';
import { CanvasserCurrentAssignmentComponent } from './canvasser-current-assignment/canvasser-current-assignment.component';
import { CanvasserUpcomingAssignmentComponent } from './canvasser-upcoming-assignment/canvasser-upcoming-assignment.component';


import { CommonModule } from '@angular/common';
import { CalendarModule, DateAdapter } from 'angular-calendar';

import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DemoUtilsModule } from './demo-utils/modules';

import { AgmCoreModule } from '@agm/core';
import { CanvasserLocationInfoComponent } from './canvasser-location-info/canvasser-location-info.component';

import { HttpModule,JsonpModule ,Http} from '@angular/http';
import {HttpClientModule} from "@angular/common/http";
import { FormsModule } from '@angular/forms';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';



import {  UpcomingAssignmentService } from './canvasser-services/canvasserUpcomingAssignment.service';
import {  CurrentAssignmentService } from './canvasser-services/canvasserCurrentAssignment.service';
import {  CalendarService } from './canvasser-services/canvasserCalendar.service';
@NgModule({
    imports: [
        RouterModule,
        RouterModule.forChild(MyCanvasserRoutes),

        CommonModule,

        CalendarModule.forRoot({
          provide: DateAdapter,
          useFactory: adapterFactory
        }),
        DemoUtilsModule,


        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyCSBQ5PQYQZwAxqE08kxZlQKR1yuwu7Xnc'
          }),

          NgbModule,
          HttpModule,
          JsonpModule,
          FormsModule,
          HttpClientModule ,


    ],
    exports: [ CanvasserCalendarComponent],
    declarations: [
        CanvasserHomeComponent,
        CanvasserCalendarComponent,
        CanvasserCurrentAssignmentComponent,
        CanvasserUpcomingAssignmentComponent,
        CanvasserLocationInfoComponent,
    ],
    providers: [UpcomingAssignmentService,CurrentAssignmentService,CalendarService],
})
export class CanvasserHomeModule { }