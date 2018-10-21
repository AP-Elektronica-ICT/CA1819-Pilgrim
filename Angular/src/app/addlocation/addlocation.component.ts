import { Component, OnInit } from '@angular/core';
import { LocationsComponent } from '../locations/locations.component';
import { LocationserviceService } from '../services/locationservice.service';

@Component({
  selector: 'app-addlocation',
  templateUrl: './addlocation.component.html',
  styleUrls: ['./addlocation.component.scss']
})
export class AddlocationComponent implements OnInit {
  
  constructor(private locationService: LocationserviceService) { }

  ngOnInit() {
    
  }
  locationName:string;
  locationLatitude:string;
  locationLongitude:string;
  locationDescription:string;
  locationQuestion:string;
  locationAnswer:string;
  locationHint1:string;
  locationHint2:string;
  
  Klik(input:String){
    switch(input){
      case "closeAdd":
        this.locationService.closeAdd();
      break;
      case "closeEdit":
        this.locationService.closeEdit();
      break;
      
    }
    
  }
}
