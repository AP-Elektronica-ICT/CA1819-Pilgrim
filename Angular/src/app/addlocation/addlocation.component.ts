import { Component, OnInit } from '@angular/core';
import { LocationsComponent } from '../locations/locations.component';
import { LocationserviceService } from '../services/locationservice.service';

@Component({
  selector: 'app-addlocation',
  templateUrl: './addlocation.component.html',
  styleUrls: ['./addlocation.component.scss']
})
export class AddlocationComponent implements OnInit {
  
  constructor(private locationservice: LocationserviceService) { }

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
      case "Open":
        this.locationservice.openEdit();
      break;
      case "Close":
        this.locationservice.closeEdit();
      break;
      
    }
    
  }
}
