import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { SpinnerService } from './spinner.service';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss'],
})
export class SpinnerComponent implements OnInit {
  constructor(private spinnerService: SpinnerService) {}
  spinner$: Observable<boolean>;

  ngOnInit() {
    this.spinner$ = this.spinnerService.shouldShowSpinner;
  }
}
