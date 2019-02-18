import { Component, OnInit } from '@angular/core';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
    editMode = false;

    productForm = new FormGroup({
      productName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
      serialNumber: new FormControl(null, [Validators.required, Validators.minLength(2)]),
      quantity: new FormControl(null, [Validators.required, Validators.minLength(2)]),
      project: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required, Validators.minLength(10)]),
      picture: new FormControl(null, [Validators.required])
    })
  constructor(private activityService: ActivityService) { }

  ngOnInit() {
  }

  onAddSave() {
    // this.activityService.addActivity('5').subscribe();
  }

  onCancel() {
    this.productForm.reset();
  }

  get productName() { return this.productForm.get('productName')}
  get serialNumber() { return this.productForm.get('serialNumber')}
  get quantity() { return this.productForm.get('quantity')}
  get project() { return this.productForm.get('project')}
  get description() { return this.productForm.get('description')}
  get picture() { return this.productForm.get('picture')}
}
