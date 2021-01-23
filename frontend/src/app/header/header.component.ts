// import { Component, OnInit } from '@angular/core';
// import { Router } from '@angular/router';
// import { faUser } from '@fortawesome/free-solid-svg-icons';
// import { SharedService } from '../auth/shared.service';

// @Component({
//   selector: 'app-header',
//   templateUrl: './header.component.html',
//   styleUrls: ['./header.component.css']
// })
// export class HeaderComponent implements OnInit {

//   faUser = faUser;
//   isLoggedIn!: boolean;
//   username!: string;

//   constructor(
//     private authService: SharedService,
//     private router: Router
//   ) { 
//     this.isLoggedIn = this.authService.isLoggedIn();
//     this.username = this.authService.getUsername();
//   }

//   ngOnInit(): void {
//   }

//   goToUserProfile(){
//     this.router.navigateByUrl('/user-profile/' + this.username);
//   }

// }

import { Component, OnInit } from '@angular/core';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { SharedService } from '../auth/shared.service';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { LoginService } from '../auth/shared/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  faUser = faUser;
  isLoggedIn!: boolean;
  username!: string;

  constructor(private sharedService: SharedService, private router: Router, private loginService: LoginService) { }

  ngOnInit() {
    this.loginService.loggedIn.subscribe((data: boolean) => {this.isLoggedIn = data;})
    this.loginService.username.subscribe((data: string) => {this.username = data;})
  }

  goToUserProfile() {
    this.router.navigateByUrl('/user-profile/' + this.username);
  }

  logout(){
    this.sharedService.logout();
  }
}
