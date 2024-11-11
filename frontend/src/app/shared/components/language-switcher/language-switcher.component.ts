import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-language-switcher',
  standalone: true,
  imports: [],
  templateUrl: './language-switcher.component.html',
  styleUrl: './language-switcher.component.scss'
})
export class LanguageSwitcherComponent {
  constructor(private translateService: TranslateService) {}

  switchLanguage(event: Event) {
    const selectElement = event.target as HTMLSelectElement; 
    const language = selectElement?.value;

    if (language) {
      this.translateService.use(language);
    }
  }
}
