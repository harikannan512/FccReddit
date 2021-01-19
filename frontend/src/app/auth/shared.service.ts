import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { SignupRequestPayload } from './signup/signup-request-payload';
import { Observable } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';
import { LoginResponsePayload } from './login/login-response-payload';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  
  refreshToken() {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUsername()
    }

    return this.httpClient.post<LoginResponsePayload>('http://localhost:8080/auth/api/refresh/token', 
      refreshTokenPayload)
        .pipe(tap(response => {
          this.localStorage.clear('authenticationToken');
          this.localStorage.clear('expiresAt');
          
          this.localStorage.store('authenticationToken', response.authenticationToken);
          this.localStorage.store('expiresAt', response.expiresAt);
        }));
  }

  getJwtToken() {
    return  this.localStorage.retrieve('authenticationToken');
  }

  getUsername(){
    return this.localStorage.retrieve('username');
  }

  getRefreshToken(){
    return this.localStorage.retrieve('refreshToken');
  }

  constructor(
    private localStorage: LocalStorageService,
    private httpClient: HttpClient
    ) { }

  signup(signupRequestPayload: SignupRequestPayload): Observable <any>{
    return this.httpClient.post('http://localhost:8080/api/auth/signup', signupRequestPayload, {responseType: 'text'})
  }

}
 