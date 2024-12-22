import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { RouterLink } from '@angular/router';
import { LanguageSwitcherComponent } from '../language-switcher/language-switcher.component';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NavbarComponent, RouterLink, LanguageSwitcherComponent, NgClass, TranslateModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  isLanguagePolish: boolean = false;
  
  constructor(private translate: TranslateService) {
  }

  ngOnInit(): void {
    this.translate.onLangChange.subscribe(() => {
      this.onLangChange();
    });
  }

  onLangChange(): void {
    this.isLanguagePolish = this.translate.currentLang === 'pl';
  }
}
