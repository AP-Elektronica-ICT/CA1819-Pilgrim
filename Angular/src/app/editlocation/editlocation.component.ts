import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-editlocation',
  templateUrl: './editlocation.component.html',
  styleUrls: ['./editlocation.component.scss']
})
export class EditlocationComponent implements OnInit {

  constructor() { }

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

}
