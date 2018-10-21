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
}
