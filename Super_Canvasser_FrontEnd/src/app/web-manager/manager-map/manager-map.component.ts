import { Component, OnInit } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ApplicationRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgmCoreModule } from '@agm/core';


import { MouseEvent } from '@agm/core';
@Component({
  selector: 'app-manager-map',
  templateUrl: './manager-map.component.html',
  styleUrls: ['./manager-map.component.css']
})
export class ManagerMapComponent implements OnInit {
  //  lat: number = 40.9257;
  //  lng: number = 73.1409;

  constructor() { }

  ngOnInit() {
  }




    // google maps zoom level
    zoom: number = 8;
  
    // initial center position for the map
    inilat: number = 51.673858;
    inilng: number = 7.815982;
  
    // clickedMarker(label: string, index: number) {
    //   console.log(`clicked the marker: ${label || index}`)
    // }
    
    // mapClicked($event: MouseEvent) {
    //   this.markers.push({
    //     lat: $event.coords.lat,
    //     lng: $event.coords.lng,
    //     draggable: true
    //   });
    // }
    
    // markerDragEnd(m: marker, $event: MouseEvent) {
    //   console.log('dragEnd', m, $event);
    // }
    

    markers: marker[] = [
      {
        lat: 51.673858,
        lng: 7.815982,
        label: 'A',
        draggable: true,
        Info:"A"
      },
      {
        lat: 51.373858,
        lng: 7.215982,
        label: 'B',
        draggable: false,
        Info:"B"
      },
      {
        lat: 51.723858,
        lng: 7.895982,
        label: 'C',
        draggable: true,
        Info:"C"
      }
    ]



    

}

// just an interface for type safety.
interface marker {
	lat: number;
	lng: number;
	label?: string;
  draggable: boolean;
  Info:String;
}
