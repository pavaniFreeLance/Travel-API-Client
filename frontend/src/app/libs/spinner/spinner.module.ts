import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { SpinnerComponent } from './spinner.component';

@NgModule({
  declarations: [SpinnerComponent],
  exports: [SpinnerComponent],
  imports: [CommonModule, ProgressSpinnerModule],
})
export class SpinnerModule {}
