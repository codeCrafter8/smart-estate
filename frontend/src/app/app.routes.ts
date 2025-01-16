import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/components/login/login.component';
import { RegisterComponent } from './features/auth/components/register/register.component';
import { HomepageComponent } from './features/homepage/homepage.component';
import { PropertiesListComponent } from './features/properties/components/properties-list/properties-list.component';
import { PropertyAdvertComponent } from './features/properties/components/property-advert/property-advert.component';
import { MyAdvertsComponent } from './features/properties/components/my-adverts/my-adverts.component';
import { PropertyDetailsComponent } from './features/properties/components/property-details/property-details.component';
import { PropertyInquiriesComponent } from './features/properties/components/property-inquiries/property-inquiries.component';
import { AdvertConfirmationComponent } from './features/properties/components/advert-confirmation/advert-confirmation.component';

export const routes: Routes = [
    { path: '', component: HomepageComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'properties', component: PropertiesListComponent },
    { path: 'advertise', component: PropertyAdvertComponent },
    { path: 'my-adverts', component: MyAdvertsComponent },
    { path: 'my-adverts/edit/:propertyId', component: PropertyAdvertComponent },
    { path: 'properties/:propertyId', component: PropertyDetailsComponent },
    { path: 'my-adverts/inquiries/:id', component: PropertyInquiriesComponent },
    { path: 'advert-confirmation', component: AdvertConfirmationComponent },
];
