import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule, 
    CommonModule,
    RouterLink,
    TranslateModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm: FormGroup;
  loginFailed = false;
  submitted = false;
  errorMessage: string = ''; 

  constructor(
    private fb: FormBuilder, 
    private authService: AuthService,
    private router: Router, 
    private translate: TranslateService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;

      this.authService.login({ email, password }).subscribe({
        next: () => {
          this.loginFailed = false;
          this.errorMessage = '';
          this.router.navigate(['/']); 
        },
        error: (err) => {
          console.error('Login failed', err);
          this.loginFailed = true;
          const translationKey = err.status === 401
            ? 'INCORRECT_EMAIL_OR_PASSWORD'
            : 'LOGIN_FAILED';
          
          this.translate.get(translationKey).subscribe((translatedMessage: string) => {
            this.errorMessage = translatedMessage;
          });
        }
      });
    }
  }
}
