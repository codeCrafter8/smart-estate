import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-advert-confirmation',
  standalone: true,
  imports: [TranslateModule, RouterModule],
  templateUrl: './advert-confirmation.component.html',
  styleUrl: './advert-confirmation.component.scss'
})
export class AdvertConfirmationComponent {

  constructor(private router: Router, private translate: TranslateService) {}

  navigateToMyAdverts() {
    this.router.navigate(['/my-adverts']);
  }
}
