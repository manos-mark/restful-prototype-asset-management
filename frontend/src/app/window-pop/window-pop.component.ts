import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-window-pop',
  templateUrl: './window-pop.component.html',
  styleUrls: ['./window-pop.component.css']
})
export class WindowPopComponent implements OnInit {
  @Input() active: Boolean;
  
  constructor(public authService: AuthService) { }

  onSubmit() {
    this.authService.logoutUser();
  }

  onCancel() {
    this.authService.windowPopFail = false;    
    this.authService.windowPopLogout = false;  
    this.authService.windowPop = false;  
  }

  ngOnInit() {
  }

}
