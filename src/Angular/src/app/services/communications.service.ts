import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ILocation } from './locations.service';

@Injectable()
export class CommunicationsService {

  private editSource = new Subject<boolean>();
  private addSource = new Subject<boolean>();
  private addLocationSource = new Subject<ILocation>();
  private editLocationSource = new Subject<ILocation>();

  edit$ = this.editSource.asObservable();
  add$ = this.addSource.asObservable();
  addLocation$ = this.addLocationSource.asObservable();
  editLocation$ = this.editLocationSource.asObservable();

  openEdit(){
    this.addSource.next(false);
    this.editSource.next(true);
  }
  closeEdit(){
    this.editSource.next(false)
  }
  openAdd(){
    this.editSource.next(false);
    this.addSource.next(true);
  }
  closeAdd(){
    this.addSource.next(false)
  }
  addLocation(location: ILocation){
    this.addLocationSource.next(location);
  }
  editLocation(editLocation : ILocation){
    this.editLocationSource.next(editLocation);
  }


}
