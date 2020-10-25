import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { DialogService } from '../libs/dialog/dialog.service';

@Injectable()
export class SimpleTravelHttpInterceptor implements HttpInterceptor {
  constructor(private errorDialogService: DialogService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error) => {
        this.errorDialogService.setErrorState(error);
        return throwError(error.message);
      })
    );
  }
}
