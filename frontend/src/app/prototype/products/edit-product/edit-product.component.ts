import { Component, OnInit } from '@angular/core';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProjectsService } from '../../projects/projects.service';
import { Project } from '../../projects/project.model';
import { ProductsService } from '../products.service';
import { ProductPicture } from '../product-picture.model';
import { Product } from '../product.model';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
    editMode = false;
    projects: Project[];
    picturesList: ProductPicture[] = [];
    // eventFileList: FileList;
    imageSrc: string[] = [];
    i = 0;
    // tempPicture: File = null;

    productForm = new FormGroup({
        productName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        serialNumber: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        quantity: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        project: new FormControl("Choose project", [Validators.required, Validators.pattern("^[0-9]+$")]),
        description: new FormControl(null, [Validators.required, Validators.minLength(1)]),
        picture: new FormControl(null, [Validators.required]),
        thumbFormGroup: new FormGroup({
            thumb: new FormControl(null, [Validators.required])
        })
    })

    constructor(private activityService: ActivityService,
        private projectService: ProjectsService,
        private productService: ProductsService) { }

    ngOnInit() {
        this.projectService.getAllProjects()
        .subscribe(
            res => this.projects = res,
            error => console.log(error)
        )
    }

    convertBytesToMegabytes(bytes,decimals) {
        if(bytes == 0) return '0 Bytes';
        var k = 1024, dm = decimals <= 0 ? 0 : decimals || 2,
        
        sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));

        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }

    readURL(eventFileList: FileList): void {
        // preview images
        if (eventFileList && eventFileList[0]) {
            const file = eventFileList[0];
    
            const reader = new FileReader();
            reader.onload = e => this.imageSrc.push(reader.result.toString());
            reader.readAsDataURL(file);
        }
        
        let tempPicture = new ProductPicture();
        tempPicture.id = this.i++;
        tempPicture.file = eventFileList.item(0);
        tempPicture.productId = 0
        tempPicture.thumb = false;
        this.picturesList.push(tempPicture)
    }

    onAddSave() {
        // if (this.editMode == false) {
        //     //save the product first
        //     this.productService.addProduct(this.productName.value, this.serialNumber.value, 
        //         this.description.value, this.quantity.value, this.project.value)
        //         .subscribe(
        //             res => {
        //                 let productId = res;
        //                 //then add activity
        //                 this.activityService.addActivity('5').subscribe();
        //                 //set the thumb pic
        //                 this.picturesList[this.thumb.value].thumb = true;
        //                 //then save the pictures
        //                 // this.picturesList.forEach(
        //                 //     (pic) => this.productService.addPicture(productId, pic)
        //                 //                 .subscribe()
        //                 // )
        //             },
        //             error => console.log(error)
        //         )
        // }
    }

    onCancel() {
        this.productForm.reset();
    }

    get productName() { return this.productForm.get('productName') }
    get serialNumber() { return this.productForm.get('serialNumber') }
    get quantity() { return this.productForm.get('quantity') }
    get project() { return this.productForm.get('project') }
    get description() { return this.productForm.get('description') }
    get picture() { return this.productForm.get('picture') }
    get thumbFormGroup() { return this.productForm.get('thumbFormGroup') }
    get thumb() { return this.productForm.get('thumbFormGroup').get('thumb') }
}
