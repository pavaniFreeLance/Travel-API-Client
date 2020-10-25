import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TableModule as NgPrimeTableModule } from 'primeng/table';
import { TableComponent } from './table.component';

@NgModule({
  declarations: [TableComponent],
  exports: [TableComponent],
  imports: [
    CommonModule,
    NgPrimeTableModule
  ]
})
export class TableModule { }
