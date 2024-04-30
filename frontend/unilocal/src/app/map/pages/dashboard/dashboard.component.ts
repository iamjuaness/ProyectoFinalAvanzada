import { Component } from '@angular/core';
import { NavComponent } from '../../shared/nav/nav.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  userLoginOn: boolean = false;
  constructor() { }
  ngOnInit():void{}
}
