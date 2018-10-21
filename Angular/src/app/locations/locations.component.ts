import { Component, OnInit } from '@angular/core';
import { LocationserviceService } from '../services/locationservice.service';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.scss']
})
export class LocationsComponent implements OnInit {

  constructor(private locationsService:LocationserviceService) { 
    this.locationsService.edit$.subscribe(
      t => {
        this.editLocation = t
      }
    )
  }

  ngOnInit() {
  }
  editLocation:boolean = false;
  addLocation:boolean = true;

  test(){
    this.editLocation = true;
  }
}
