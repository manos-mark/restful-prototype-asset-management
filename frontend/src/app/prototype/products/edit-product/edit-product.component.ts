import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProjectsService } from '../../projects/projects.service';
import { Project } from '../../projects/project.model';
import { ProductsService } from '../products.service';
import { ProductPicture } from '../product-picture.model';
import { Product } from '../product.model';
import { Statuses } from '../../status.enum';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit, OnDestroy {
    product: Product;
    windowPop = false;
    windowPopFail = false;
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
        statusId: new FormControl(null, [Validators.required, Validators.pattern("^[0-9]+$")]),
        project: new FormControl("Choose project", [Validators.required, Validators.pattern("^[0-9]+$")]),
        description: new FormControl(null, [Validators.required, Validators.minLength(1)]),
        picture: new FormControl(null, [Validators.required]),
        thumbFormGroup: new FormGroup({
            thumb: new FormControl(null, [Validators.required])
        })
    })

    constructor(private activityService: ActivityService,
        private projectService: ProjectsService,
        private productService: ProductsService,
        private router: Router,
        private route: ActivatedRoute) { }

    ngOnInit() {
        // when add new project status always will be new and disabled
        this.productForm.controls.statusId.setValue(Statuses.NEW);
        this.productForm.controls.statusId.disable();
        // get projects for the dropdown
        this.projectService.getAllProjects()
            .subscribe(
                res => this.projects = res,
                error => console.log(error)
            )
        // on edit mode init the fields        
        if (this.productService.editMode) {
            this.route.queryParams.subscribe(
                res => {
                    this.productService.getProductById(res.productId).subscribe(
                        res => {
                            this.product = new Product(res);
                            this.productForm.controls.productName.setValue(this.product.productName);
                            this.productForm.controls.serialNumber.setValue(this.product.serialNumber);
                            this.productForm.controls.quantity.setValue(this.product.quantity);
                            this.productForm.controls.statusId.setValue(this.product.status.id);
                            this.productForm.controls.project.setValue(this.product.projectId);
                            this.productForm.controls.description.setValue(this.product.description);
                            // this.productForm.controls.picture.setValue(this.product.picture);
                            // this.productForm.controls.thumbFormGroup.controls.thumb
                        },
                        error => console.log(error)
                    )
                },
                error => console.log(error)
            )
        }
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

        let tempProduct = new Product({
            productName: this.productName.value,
            serialNumber: this.serialNumber.value,
            quantity: this.quantity.value,
            statusId: this.statusId.value,
            project: this.project.value,
            description: this.description.value
        });
        // on edit mode update
        if (this.productService.editMode) {
            this.productService.updateProduct(tempProduct)
                    .subscribe(
                        res => {
                            // this.activityService.addActivity(Statuses.IN_PROGRESS.toString())
                            //     .subscribe(
                            //         res => this.router.navigate(['/prototype/projects']),
                            //         error => console.log(error)
                            //     )
                        },
                        error => {
                            console.log(error)
                            this.windowPop = true;
                            this.windowPopFail = true;
                        }
                    )
        }
        // else add new product
        else {
            this.productService.addProduct(tempProduct)
                    .subscribe(
                        res => {
                            // this.activityService.addActivity(Statuses.NEW.toString())
                            //     .subscribe(
                            //         res => this.router.navigate(['/prototype/projects']),
                            //         error => console.log(error)
                            //     )
                        },
                        error => {
                            console.log(error)
                            this.windowPop = true;
                            this.windowPopFail = true;
                        }
                    )
        }
    }

    onCancel() {
        this.productForm.reset();
        this.router.navigate(['prototype','products']);
    }

    ngOnDestroy() {
        this.productService.editMode = false;
    }

    get productName() { return this.productForm.get('productName') }
    get serialNumber() { return this.productForm.get('serialNumber') }
    get quantity() { return this.productForm.get('quantity') }
    get project() { return this.productForm.get('project') }
    get statusId() { return this.productForm.get('statusId') }
    get description() { return this.productForm.get('description') }
    get picture() { return this.productForm.get('picture') }
    get thumbFormGroup() { return this.productForm.get('thumbFormGroup') }
    get thumb() { return this.productForm.get('thumbFormGroup').get('thumb') }
}
