import { Component, OnInit } from '@angular/core';
import { LocationsComponent } from '../locations/locations.component';
import { CommunicationsService } from '../services/communications.service';
import { LocationsService, INewLocation } from '../services/locations.service';


@Component({
  selector: 'app-addlocation',
  templateUrl: './addlocation.component.html',
  styleUrls: ['./addlocation.component.scss']
})
export class AddlocationComponent implements OnInit {
  
  constructor(private commService: CommunicationsService, private locService: LocationsService) { }

  ngOnInit() {
    
  }
  locationName:string;
  locationLatitude:number;
  locationLongitude:number;
  locationDescription:string;
  locationCryptic:string;
  locationAnswer:string;
  locationHint1:string;
  locationHint2:string;
  
  
  

  addLocation(){
    var location : INewLocation = {
      naam: this.locationName,
      lat: this.locationLatitude,
      long: this.locationLongitude,
      description: this.locationDescription,
      crypticClue: this.locationCryptic,
      answer: this.locationAnswer,
      hint1: this.locationHint1,
      hint2: this.locationHint2 
    }
    this.locService.addLocation(location).subscribe(data => {
      this.commService.addLocation(data);
    },
    error => {
      if ( error.status == 400){
        
      }
    })
  }

  closeAddLocation(){
    this.commService.closeAdd();
  }
}
