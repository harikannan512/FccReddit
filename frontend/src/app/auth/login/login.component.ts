import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginRequestPayload } from './login-request-payload';
import { LoginService } from "../shared/login.service";
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  loginRequestPayload: LoginRequestPayload;
  registerSuccessMessage!: string;
  isError!: boolean;

  constructor(
    private toastr: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private loginService: LoginService
    ) { 
    this.loginRequestPayload = {
      username: '',
      password: ''
    }
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    })
  
    this.activatedRoute.queryParams
      .subscribe(params => {
        if (params.registered !== undefined && params.registered == 'true'){
          this.toastr.success('SignUp Successful');
          this.registerSuccessMessage = 'Please check you inbox for activation link to activate your account';
        }
      })
  
  }

  login(){
    this.loginRequestPayload.username = this.loginForm.get('username')?.value;
    this.loginRequestPayload.password = this.loginForm.get('password')?.value;
    
    this.loginService.login(this.loginRequestPayload).subscribe(data => {
      if(data) {
        this.isError = false;
        this.router.navigateByUrl('/');
        this.toastr.success('Login Successful');
      } else{
        this.isError = true;
      }
    })
  }

}
