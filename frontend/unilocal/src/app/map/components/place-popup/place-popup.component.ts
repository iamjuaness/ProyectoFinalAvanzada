import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-place-popup',
  templateUrl: './place-popup.component.html',
  styleUrls: ['./place-popup.component.css']
})
export class PlacePopupComponent {
  @Input() place: any;
  @Output() close = new EventEmitter<void>();

  closePopup() {
    this.close.emit();
  }
}
