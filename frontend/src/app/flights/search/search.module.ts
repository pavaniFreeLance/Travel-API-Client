import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from '../../libs/dropdown/dropdown.module';
import { SpinnerModule } from '../../libs/spinner/spinner.module';
import { TableModule } from '../../libs/table/table.module';
import { SearchComponent } from './search.component';
import { SearchRoutingModule } from './search.routing.module';

@NgModule({
  declarations: [SearchComponent],
  imports: [
    CommonModule,
    FormsModule,
    SearchRoutingModule,
    DropdownModule,
    ButtonModule,
    TableModule,
    SpinnerModule,
  ],
})
export class SearchModule {}
