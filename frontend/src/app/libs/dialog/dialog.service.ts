import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DialogService {
  constructor() {}

  private subject = new Subject();

  // Other components can listen to this!
  private error$ = this.subject.asObservable();

  setErrorState(error: Error) {
    this.subject.next(error);
    console.log('error', error)
  }

  getErrorState() {
    return this.error$;
  }
}
