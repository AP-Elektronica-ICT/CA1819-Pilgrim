import { Component, OnInit } from '@angular/core';
import { CommunicationsService } from '../services/communications.service';
import { LocationsService, ILocation } from '../services/locations.service';

@Component({
  selector: 'app-editlocation',
  templateUrl: './editlocation.component.html',
  styleUrls: ['./editlocation.component.scss']
})
export class EditlocationComponent implements OnInit {

  location: ILocation;
  locationID: number;
  locationName:string;
  locationLatitude:number;
  locationLongitude:number;
  locationDescription:string;
  locationQuestion:string;
  locationAnswer:string;
  locationHint1:string;
  locationHint2:string;

  constructor(private commService: CommunicationsService, private locService: LocationsService) { 
    this.commService.editLocation$.subscribe(s =>{
      this.location = s;
      this.locationName = s.naam;
      this.locationLatitude = s.lat;
      this.locationLongitude = s.long;
      this.locationDescription = s.description;
      this.locationQuestion = s.crypticClue;
      this.locationAnswer = s.answer;
      this.locationHint1 = s.hint1;
      this.locationHint2 = s.hint2;
    })
  }

  ngOnInit() {
  }

  

  editLocation(){
    var location1: ILocation = {
      id : this.location.id,
      naam : this.locationName,
      lat : this.locationLatitude,
      long : this.locationLongitude,
      description : this.locationDescription,
      crypticClue : this.locationQuestion,
      answer : this.locationAnswer,
      hint1 : this.locationHint1,
      hint2 : this.locationHint2,
    }
    this.locService.updateLocation(location1).subscribe(data =>{
      this.location.naam = this.locationName;
      this.location.lat = this.locationLatitude;
      this.location.long = this.locationLongitude;
      this.location.description = this.locationDescription;
      this.location.crypticClue = this.locationQuestion;
      this.location.answer = this.locationAnswer;
      this.location.hint1 = this.locationHint1;
      this.location.hint2 = this.locationHint2;
      this.location = null;
    },
    error =>{

    })
  }

  closeEditLocation(){
    this.location = null;
  }

}
