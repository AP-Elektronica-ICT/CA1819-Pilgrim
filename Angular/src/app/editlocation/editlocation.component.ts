import { Component, OnInit } from '@angular/core';
import { LocationserviceService } from '../services/locationservice.service';

@Component({
  selector: 'app-editlocation',
  templateUrl: './editlocation.component.html',
  styleUrls: ['./editlocation.component.scss']
})
export class EditlocationComponent implements OnInit {

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

  Klik(input:string){
    switch(input){
      case "closeEdit":
        this.locationService.closeEdit();
      break;
    }
  }

}
