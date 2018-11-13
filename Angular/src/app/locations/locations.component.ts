import { Component, OnInit } from '@angular/core';
import { CommunicationsService } from '../services/communications.service';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.scss']
})
export class LocationsComponent implements OnInit {

  constructor(private commService:CommunicationsService) { 
    this.commService.edit$.subscribe(
      t => {
        this.editLocation = t;
      }
    )
    this.commService.add$.subscribe(
      s => {
        this.addLocation = s;
      }
    )
  }

  ngOnInit() {
  }
  editLocation:boolean = false;
  addLocation:boolean = false;

  test(){
    this.editLocation = true;
  }
}
