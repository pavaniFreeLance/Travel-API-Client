import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { SpinnerService } from '../../libs/spinner/spinner.service';
import { SearchService } from './service/search.service';
import { IAirportLocation, IFare } from './store/search.store';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent implements OnInit {
  constructor(private searchService: SearchService, private spinnerService: SpinnerService) {}

  countries$: Observable<IAirportLocation[]>;
  fare$: Observable<IFare>;
  
  source: IAirportLocation;
  destination: IAirportLocation;

  ngOnInit() {
    this.countries$ = this.searchService.loadAllAirportLocations();
  }

  showFares() {
    this.spinnerService.show();
    this.fare$ = this.searchService.getFare(
      this.source.code,
      this.destination.code
    );
  }
}
