import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationserviceService {

  private editSource = new Subject<boolean>();
  private addSource = new Subject<boolean>();

  edit$ = this.editSource.asObservable();
  add$ = this.addSource.asObservable();

  openEdit(){
    this.editSource.next(true)
  }
  closeEdit(){
    this.editSource.next(false)
  }
  openAdd(){
    this.addSource.next(true)
  }
  closeAdd(){
    this.addSource.next(false)
  }
}
