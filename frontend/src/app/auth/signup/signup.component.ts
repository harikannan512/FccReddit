import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SharedService } from '../shared.service';
import { SignupRequestPayload } from './signup-request-payload';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupForm!: FormGroup;
  signupRequestPayload!: SignupRequestPayload;

  constructor(
    private toastr: ToastrService,
    private router: Router,
    private sharedService: SharedService
    ) { 
    this.signupRequestPayload = {
      username: '',
      email: '',
      password: ''
    }
  }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
  }

  signup(){
    this.signupRequestPayload.email = this.signupForm.get('email')?.value;
    this.signupRequestPayload.username = this.signupForm.get('username')?.value;
    this.signupRequestPayload.password = this.signupForm.get('password')?.value;
  
    this.sharedService.signup(this.signupRequestPayload)
    .subscribe(() => {
      this.router.navigate(['/login'],
       { queryParams: {registered:'true' } });
    }, () => {
      this.toastr.error('Registration Failed! Please try again');
    } );
  }

}
