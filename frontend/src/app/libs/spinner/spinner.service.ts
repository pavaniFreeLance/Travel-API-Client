import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SpinnerService {
  private _spinnerState$ = new Subject<boolean>();

  public show() {
    this._spinnerState$.next(true);
  }

  public hide() {
    this._spinnerState$.next(false);
  }

  get shouldShowSpinner() {
    return this._spinnerState$.asObservable();
  }
}
