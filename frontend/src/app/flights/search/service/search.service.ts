import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { shareReplay, take, tap } from 'rxjs/operators';
import { IAirportLocation, IFare, SearchDataStore } from '../store/search.store';

const BASE_URL = 'http://localhost:8085/travel';
const URL_GET_AIRPORTS = `${BASE_URL}/airports`;
const URL_GET_FARE = `${BASE_URL}/fares`;

@Injectable({ providedIn: 'root' })
export class SearchService {
  constructor(private http: HttpClient, private store: SearchDataStore) {}

  loadAllAirportLocations() {
    this.getAllAirportLocations();

    return this.store.getAiportLocations();
  }

  getFare(source: string, destination: string) {
    this.fetchFare(source, destination);

    return this.store.getTravelFare();
  }

  private fetchFare(source: string, destination: string) {
    this.http
      .get(`${URL_GET_FARE}/${source}/${destination}?currency=EUR`)
      .pipe(
        take(1),
        tap((data: IFare) => this.store.setTravelFare(data))
      )
      .subscribe();
  }

  private getAllAirportLocations() {
    this.http
      .get(URL_GET_AIRPORTS)
      .pipe(
        take(1),
        tap((data: IAirportLocation[]) => this.store.setAirportLocations(data)),
        shareReplay()
      )
      .subscribe();
  }
}


