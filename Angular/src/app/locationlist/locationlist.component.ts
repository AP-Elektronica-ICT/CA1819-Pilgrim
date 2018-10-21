import { Component, OnInit } from '@angular/core';
import { LocationserviceService } from '../services/locationservice.service';

@Component({
  selector: 'app-locationlist',
  templateUrl: './locationlist.component.html',
  styleUrls: ['./locationlist.component.scss']
})
export class LocationlistComponent implements OnInit {

  constructor(private locationservice:LocationserviceService) { }

  ngOnInit() {
  }

  Klik(input:string){
    switch(input){
      case "AddLocation":
        this.locationservice.openAdd();
      break;
      case "Update":
        this.locationservice.openEdit();
      break;
    }
  }

}
