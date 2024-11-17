import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { NgIf } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, NgIf, TranslateModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit {
  isLoggedIn: boolean = false;
  isDropdownOpen: boolean = false;

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
    });
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  closeDropdown(): void {
    this.isDropdownOpen = false;
  }

  logOut() {
    this.authService.logOut(); 
    this.router.navigate(['/']); 
  }
}
