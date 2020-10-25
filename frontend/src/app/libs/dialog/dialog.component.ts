import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil, tap } from 'rxjs/operators';
import { DialogService } from './dialog.service';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
})
export class DialogComponent implements OnInit, OnDestroy {
  showDialog = false;
  errorMessage = '';

  destroy$ = new Subject();

  constructor(private dialogService: DialogService) {}

  ngOnInit() {
    this.dialogService
      .getErrorState()
      .pipe(
        tap(() => (this.showDialog = true)),
        takeUntil(this.destroy$)
      )
      .subscribe((error: Error) => (this.errorMessage = error.message));
  }

  close() {
    this.showDialog = false;
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
