import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { DialogModule as NgPrimeDialog } from 'primeng/dialog';
import { DialogComponent } from './dialog.component';

@NgModule({
  declarations: [DialogComponent],
  imports: [CommonModule, NgPrimeDialog, ButtonModule],
  exports: [DialogComponent],
})
export class DialogModule {}
