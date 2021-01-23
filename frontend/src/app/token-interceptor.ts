import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, throwError } from "rxjs";
import { catchError, filter, switchMap, take } from "rxjs/operators";
import { LoginResponsePayload } from "./auth/login/login-response-payload";
import { SharedService } from "./auth/shared.service";


@Injectable({
    providedIn: 'root'
})


export class TokenInterceptor implements HttpInterceptor{

    isTokenRefreshing: boolean = false;
    refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

    constructor(public sharedService: SharedService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent <any>>{
            const jwtToken = this.sharedService.getJwtToken();

            // if(req.url.indexOf('refresh') !== -1 || req.url.indexOf('login')){
            //     return next.handle(req);
            // }
            
            if (jwtToken) {
            return next.handle(this.addToken(req, jwtToken)).pipe(catchError(error => {
                if (error instanceof HttpErrorResponse
                    && error.status === 403) {
                    return this.handleAuthErrors(req, next);
                } else {
                    return throwError(error);
                }
            }));
        }

        return next.handle(req);
    }

    private handleAuthErrors(req: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>> {
    if (this.isTokenRefreshing === false) {
        this.isTokenRefreshing = true;
        this.refreshTokenSubject.next(null);

        return this.sharedService.refreshToken().pipe(
            switchMap((refreshTokenResponse: LoginResponsePayload) => {
                this.isTokenRefreshing = false;
                this.refreshTokenSubject
                    .next(refreshTokenResponse.authenticationToken);
                return next.handle(this.addToken(req,
                    refreshTokenResponse.authenticationToken));
            })
        )
    }  
    else {
            return this.refreshTokenSubject.pipe(
                filter(result => result !== null),
                take(1),
                switchMap((res) => {
                    return next.handle(this.addToken(req,
                        this.sharedService.getJwtToken()))
                })
            );
        }        
    } 


    addToken(req: HttpRequest<any>, jwtToken: any){
        return req.clone({
            headers: req.headers.set('Authorization', 
                'Bearer ' + jwtToken)
        });
    }


}
