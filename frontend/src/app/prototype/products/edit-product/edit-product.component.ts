import { Component, OnInit } from '@angular/core';
import { ActivityService } from 'src/app/general/home/activity/activity.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {

  constructor(private activityService: ActivityService) { }

  ngOnInit() {
  }

  onAddSave() {
    this.activityService.addActivity('5').subscribe();
  }

}
