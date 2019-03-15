import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { NotificationService } from './notification.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnChanges {
    @Input() active: Boolean;
    @Output() activeChange = new EventEmitter<Boolean>();

    constructor(private notificationService: NotificationService) { }

    ngOnChanges() {
        // console.log(this.notificationService)
        // setTimeout(() => this.onClose(), 4000);
    }

    onClose() {
        this.notificationService.hideNotification();
        this.activeChange.emit(false); 
    }

}
