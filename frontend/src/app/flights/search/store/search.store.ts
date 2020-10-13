import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export interface IAirportLocation {
  code: string;
  name: string;
  description: string;
}

export interface IFare {
  amount: number;
  curreny: string;
  origin: string;
  destination: string;
  originName?: string;
  destinationName?: string;
}

@Injectable({ providedIn: 'root' })
export class SearchDataStore {
  constructor() {}
  private _airportsLocations$ = new Subject<IAirportLocation[]>();
  private _travelFare$ = new Subject<IFare>();

  setAirportLocations(locations: IAirportLocation[]) {
    this._airportsLocations$.next(locations);
  }
  getAiportLocations() {
    return this._airportsLocations$.asObservable();
  }

  setTravelFare(fare: IFare) {
    return this._travelFare$.next(fare);
  }
  getTravelFare() {
    return this._travelFare$.asObservable();
  }
}
