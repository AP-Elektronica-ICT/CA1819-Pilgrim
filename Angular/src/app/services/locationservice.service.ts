import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationserviceService {

  private editSource = new Subject<boolean>();

  edit$ = this.editSource.asObservable();

  openEdit(){
    this.editSource.next(true)
  }
  closeEdit(){
    this.editSource.next(false)
  }
}
