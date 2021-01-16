import { Injectable } from '@angular/core';
import { LoginRequestPayload } from '../login/login-request-payload';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { LoginResponsePayload } from '../login/login-response-payload';
import { LocalStorageService } from 'ngx-webstorage';
import { map, tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient, 
    private localStorage: LocalStorageService) { }

    login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
      return this.httpClient.post<LoginResponsePayload>('http://localhost:8080/api/auth/login',
        loginRequestPayload).pipe(map(data => {
          this.localStorage.store('authenticationToken', data.authenticationToken);
          this.localStorage.store('username', data.username);
          this.localStorage.store('refreshToken', data.refreshToken);
          this.localStorage.store('expiresAt', data.expiresAt);
  
          return true;
        }));
    }
}
