import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { map, take, tap } from 'rxjs/operators';
import { IFare } from 'src/app/flights/search/store/search.store';
import { SpinnerService } from '../spinner/spinner.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent {
  constructor(private spinnerService: SpinnerService) {}
  
  public _fares: IFare[] = [];

  @Input()
  set fare$(value: Observable<IFare>) {
    if (value) {
      value
        .pipe(
          take(1),
          map((value) => [value]),
          tap((_) => this.spinnerService.hide())
        )
        .subscribe((fares: IFare[]) => (this._fares = fares));
    }
  }
  get fares() {
    return this._fares as any;
  }
}
