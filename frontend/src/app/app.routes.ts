import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/components/login/login.component';
import { RegisterComponent } from './features/auth/components/register/register.component';
import { HomepageComponent } from './features/homepage/homepage.component';
import { PropertiesListComponent } from './features/properties/components/properties-list/properties-list.component';
import { PropertyAdvertComponent } from './features/properties/components/property-advert/property-advert.component';
import { MyAdvertsComponent } from './features/properties/components/my-adverts/my-adverts.component';

export const routes: Routes = [
    { path: '', component: HomepageComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'properties', component: PropertiesListComponent },
    { path: 'advertise', component: PropertyAdvertComponent },
    { path: 'my-adverts', component: MyAdvertsComponent },
];
