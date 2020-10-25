import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.scss'],
})
export class DropdownComponent {
  @Input() public source$: Observable<any>;
  @Input() public label: string;

  @Output() selected = new EventEmitter();

  onChange(event: any) {
    this.selected.emit(event);
  }
}
