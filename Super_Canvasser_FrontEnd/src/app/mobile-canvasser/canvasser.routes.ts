import { RouterModule } from '@angular/router';
import { CanvasserHomeComponent } from './canvasser-home/canvasser-home.component';

import { CanvasserCalendarComponent } from './canvasser-calendar/canvasser-calendar.component';
import { CanvasserCurrentAssignmentComponent } from './canvasser-current-assignment/canvasser-current-assignment.component';
import { CanvasserUpcomingAssignmentComponent } from './canvasser-upcoming-assignment/canvasser-upcoming-assignment.component';
import { CanvasserLocationInfoComponent } from './canvasser-location-info/canvasser-location-info.component';




export const MyCanvasserRoutes=[
	{
        path:'',
        component:CanvasserHomeComponent,
        children:[{
            path:'CanvasserCalendar',
            component: CanvasserCalendarComponent

        },{
            path:"CanvasserCurrentAssignment",
            component:CanvasserCurrentAssignmentComponent
        },
       {
           path:'CanvasserUpcomingAssignment',
           component:CanvasserUpcomingAssignmentComponent
       } ,
    {
        path:"CanvasserLocationInfo",
        component: CanvasserLocationInfoComponent
    },


    ]

    }
];