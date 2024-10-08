import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, AbstractControlOptions } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule, 
    CommonModule,
    RouterLink,
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;
  registrationFailed = false;
  submitted = false; 
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder, 
    private authService: AuthService
  ) {
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator } as AbstractControlOptions); 
  }

  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }

  get confirmPassword() {
    return this.registerForm.get('confirmPassword');
  }

  passwordMatchValidator(formGroup: FormGroup) {
    return formGroup.get('password')?.value === formGroup.get('confirmPassword')?.value 
      ? null : { mismatch: true };
  }

  onSubmit() {
    this.submitted = true; 

    if (this.registerForm.valid) {
      const { email, password } = this.registerForm.value;
      
      this.authService.signUp({ email, password }).subscribe({
        next: (response) => {
          console.log('Registration successful', response);
          this.registrationFailed = false;
          this.errorMessage = '';
        },
        error: (err) => {
          console.error('Registration failed', err);
          this.registrationFailed = true;  
          this.errorMessage = err.status === 409 
            ? 'User with this email already exists.' 
            : 'An error occurred during registration. Please try again.';
        }
      });
    }
  }
}
