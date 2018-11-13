import { Component, OnInit } from '@angular/core';
import { CommunicationsService } from '../services/communications.service';
import { LocationsService, ILocation } from '../services/locations.service';

@Component({
  selector: 'app-locationlist',
  templateUrl: './locationlist.component.html',
  styleUrls: ['./locationlist.component.scss']
})
export class LocationlistComponent implements OnInit {

  Locaties : ILocation[] = [];
  constructor(private commService:CommunicationsService, private locService: LocationsService) { 
    this.Load();
    this.commService.addLocation$.subscribe(s =>{
      this.addLocation(s);
    })

  }


  ngOnInit() {
  }

  Klik(input:string){
    switch(input){
      case "AddLocation":
        this.commService.openAdd();
      break;
      case "Update":
        this.commService.openEdit();
      break;
    }
  }

  addLocation(newLocation: ILocation){
    this.Locaties.push(newLocation);
  }

  deleteLocation(id: number){
    this.locService.deleteLocation(id).subscribe(s =>{
      this.Locaties.forEach(r => {
        if (r.id == id)
          this.Locaties.splice(this.Locaties.indexOf(r), 1);
      })
    })
  }

  Load(){
    this.locService.getLocations().subscribe(s => {
      s.forEach(r => {
        this.Locaties.push(r);
      });
    })
  }

  editLocation(index: number){
    this.commService.editLocation(this.Locaties[index]);
    this.commService.openEdit();
  }
}
