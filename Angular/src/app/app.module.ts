import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LocationsComponent } from './locations/locations.component';
import { AddlocationComponent } from './addlocation/addlocation.component';
import { FormsModule } from '@angular/forms';
import { LocationlistComponent } from './locationlist/locationlist.component';
import { EditlocationComponent } from './editlocation/editlocation.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
    PageNotFoundComponent,
    LocationsComponent,
    AddlocationComponent,
    LocationlistComponent,
    EditlocationComponent
    
  ],
  imports: [
    BrowserModule,
    MDBBootstrapModule.forRoot(),
    RouterModule.forRoot([
      {path: 'home', component: HomeComponent},
      {path: 'login', component: LoginComponent},
      {path: 'locations', component: LocationsComponent},
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {path: '**', component: PageNotFoundComponent},
    ], {useHash: true}), 
    FormsModule
    
  ],
  schemas: [ NO_ERRORS_SCHEMA ], 
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
