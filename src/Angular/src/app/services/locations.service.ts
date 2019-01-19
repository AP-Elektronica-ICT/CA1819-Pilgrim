import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class LocationsService {

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin':"*"
    })
  };

  private baseURL = 'http://pilgrimapp.azurewebsites.net/api/'

  getLocations():Observable<ILocation[]>{
    return this.http.get<ILocation[]>(this.baseURL + `locations`, this.httpOptions);
  }

  addLocation(newLocation : INewLocation):Observable<ILocation>{
    return this.http.post<ILocation>(this.baseURL + 'locations', newLocation, this.httpOptions);
  }

  deleteLocation(id : number):Observable<ILocation>{
    return this.http.delete<ILocation>(this.baseURL + `locations/${id}`, this.httpOptions);
  }

  updateLocation(updateLocation : ILocation):Observable<ILocation>{
    return this.http.put<ILocation>(this.baseURL + `locations`, updateLocation, this.httpOptions);
  }

  
}

export interface ILocation {
  id: number;
  naam: string;
  description: string;
  lat: number;
  long: number;
  crypticClue: string;
  hint1: string;
  hint2: string;
  answer: string;
  base64: string;
}

export interface INewLocation {
  naam: string;
  description: string;
  lat: number;
  long: number;
  crypticClue: string;
  hint1: string;
  hint2: string;
  answer: string;
  base64: string;
}
