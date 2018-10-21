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
        this.editLocation = t;
      }
    )
    this.locationsService.add$.subscribe(
      s => {
        this.addLocation = s;
      }
    )
  }

  ngOnInit() {
  }
  editLocation:boolean = true;
  addLocation:boolean = true;

  test(){
    this.editLocation = true;
  }
}
