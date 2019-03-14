import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { ProjectsService } from '../../projects/projects.service';
import { Project } from '../../projects/project.model';
import { ProductsService } from '../products.service';
import { Product } from '../product.model';
import { Statuses } from '../../status.enum';
import { Router, ActivatedRoute } from '@angular/router';
import { WindowPopService } from 'src/app/shared/window-pop/window-pop.service';
import { ProductPicture } from '../../product-picture.model';
import { Observer, Subscription } from 'rxjs';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit, OnDestroy {
    public static readonly MAX_UPLOAD_FILES_SIZE = 12582912;
    public static readonly MAX_UPLOAD_FILES_LENGTH = 10;
    product: Product;
    projects: Project[];
    pictures: ProductPicture[] = [];
    imageSrc: string[] = [];
    computedPicturesListSize: number = 0;
    computedPicturesLength: number = 0;
    deleteImageSubscription: Subscription = null;
    isThumbSelected: Boolean = false;
    i = 0;

    productForm = new FormGroup({
        productName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        serialNumber: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        quantity: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        statusId: new FormControl(null, [Validators.required, Validators.pattern("^[0-9]+$")]),
        project: new FormControl("Choose project", [Validators.required, Validators.pattern("^[0-9]+$")]),
        description: new FormControl(null, [Validators.required, Validators.minLength(1)]),
        uploadPicture: new FormControl(null),
        thumbArray: new FormArray([],Validators.required)
    })

    constructor(private activityService: ActivityService,
        private projectService: ProjectsService,
        private productService: ProductsService,
        private router: Router,
        private route: ActivatedRoute,
        private windowPopService: WindowPopService) { }

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
        this.route.queryParams.subscribe(
            res => { this.productForm.controls.project.setValue(res.projectId) }
        )
        // on edit mode init the fields        
        if (this.editMode) {
            this.productForm.controls.statusId.enable();
            this.route.queryParams.subscribe(
                res => {
                    this.productService.getProductById(res.productId).subscribe(
                        res => {
                            this.product = new Product(res.body);
                            this.productForm.controls.productName.setValue(this.product.productName);
                            this.productForm.controls.serialNumber.setValue(this.product.serialNumber);
                            this.productForm.controls.quantity.setValue(this.product.quantity);
                            this.productForm.controls.statusId.setValue(this.product.status.id);
                            this.productForm.controls.project.setValue(this.product.projectId);
                            this.productForm.controls.description.setValue(this.product.description);
                            
                            this.productService.getPicturesByProductId(this.product.id)
                                .subscribe(
                                    res => {
                                        res.map( (picture, index) => {
                                            let tempPicture = new ProductPicture(picture);
                                            this.pictures.push(tempPicture);

                                            this.thumbArray.push(new FormControl(null))
                                            
                                            if (picture.id == this.product.thumbPictureId) {
                                                tempPicture.isThumb = true;
                                                this.thumbArray.controls[index].setValue(index);
                                                this.isThumbSelected = true;
                                            }
                                        })
                                        this.computePicturesListSize();
                                        this.computePicturesLength();
                                    },
                                    error => console.log(error)
                                );

                        },
                        error => console.log(error)
                    )
                },
                error => console.log(error)
            )
        }
    }

    onSelectThumb(index: number) {
        this.thumbArray.controls.forEach(control => {
            control.setValue(null);
        });
        this.thumbArray.controls[index].setValue(index);
        this.isThumbSelected = true;

        this.pictures.forEach((picture, i) => {
            if (index == i) {
                picture.isThumb = true;
            }
            else {
                picture.isThumb = false;
            }
        })
    }

    onUploadPicture(eventFileList: FileList): void {
        this.pictures.push(new ProductPicture({
            id: undefined,
            productId: undefined,
            name: eventFileList.item(0).name,
            size: eventFileList.item(0).size,
            file: eventFileList.item(0)
        }))
        this.computedPicturesLength++;
        this.computedPicturesListSize += eventFileList.item(0).size;
        this.thumbArray.push(new FormControl(null))
    }

    onAddSave() {
        let tempProduct = new Object({
            productName: this.productName.value,
            serialNumber: this.serialNumber.value,
            quantity: this.quantity.value,
            statusId: this.statusId.value,
            projectId: this.project.value,
            description: this.description.value,
            thumbPictureIndex: null
        });

        this.pictures.forEach((picture, index) => {
            if (picture.isThumb) {
                tempProduct['thumbPictureIndex'] = index;
            }
        });
        // console.log(tempProduct['thumbPictureIndex'])
        // console.log(tempProduct)
        // console.log(this.pictures)
        // on edit mode update
        if (this.editMode) {
            this.updateProduct(tempProduct);
        }
        // else add new product
        else {
            this.saveProduct(tempProduct);
        }
    }

    onDeletePicture(pictureIndex: number, picture: ProductPicture) {
        this.windowPopService.title = "Delete Image";
        this.windowPopService.context = "Are you sure?";
        this.windowPopService.details = "This image will be deleted permanently.";
        this.windowPopService.deleteImage = true;
        this.windowPopService.activate = true;
        
        this.deleteImageSubscription = this.productService.deleteImageConfirmed
            .subscribe( res => {

                // if this image is new, just delete from list 
                if (this.pictures[pictureIndex].type.match("new")) {
                    // check if the picture is thumb, to disable the form
                    if (this.pictures[pictureIndex].isThumb) {
                        this.isThumbSelected = false;
                    }
                    this.computedPicturesListSize -= this.pictures[pictureIndex].size;
                    this.pictures.splice(pictureIndex,1);
                    this.thumbArray.removeAt(pictureIndex);
                }
                // else set the type to deleted, dont remove from the array
                else if (this.pictures[pictureIndex].type.match("existing")) {
                    // check if the picture is thumb, to disable the form
                    if (picture.isThumb) {
                        this.isThumbSelected = false;
                        console.log('thumb id: '+picture.id)
                    }
                    this.pictures.forEach(item => {
                        if (picture.id == item.id) {
                            picture.type = "deleted";
                            this.computedPicturesListSize -= picture.size;
                        }
                    })
                }
                
                this.computedPicturesLength--;
                this.deleteImageSubscription.unsubscribe();
            })
        }

    onCancel() {
        this.productForm.reset();
        this.router.navigate(['prototype','products']);
    }

    ngOnDestroy() {
        this.productService.editMode = false;
        if (this.deleteImageSubscription) {
            this.deleteImageSubscription.unsubscribe();
        }
    }

    updateProduct(product) {
        this.productService.updateProduct(product, this.pictures, this.product.id)
            .subscribe(
                res => {
                    // this.activityService.addActivity()
                    //     .subscribe(
                    //         res => this.router.navigate(['/prototype/projects']),
                    //         error => console.log(error)
                    //     )
                    this.router.navigate(['/prototype/products'])
                },
                error => {
                    console.log(error)
                    this.windowPopService.title = "Update product Failed";
                    this.windowPopService.context = "Your request is not successful!";
                    this.windowPopService.details = "Try again with different credentials.";
                    this.windowPopService.activate = true;
                }
            )
    }
    
    saveProduct(product) {
        this.productService.addProduct(product, this.pictures)
            .subscribe(
                res => {
                    // this.activityService.addActivity()
                    //     .subscribe(
                    //         res => this.router.navigate(['/prototype/projects']),
                    //         error => console.log(error)
                    //     )
                    this.router.navigate(['/prototype/products'])
                },
                error => {
                    console.log(error)
                    this.windowPopService.title = "Add new product Failed";
                    this.windowPopService.context = "Your request is not successful!";
                    this.windowPopService.details = "Try again with different credentials.";
                    this.windowPopService.activate = true;
                }
            )
    }

    convertBytesToMegabytes(bytes,decimals) {
        if(bytes == 0) return '0 Bytes';
        var k = 1024, dm = decimals <= 0 ? 0 : decimals || 2,
        
        sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));

        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }

    computePicturesListSize() {
        let totalSize = 0;

        this.pictures.forEach(element => {
            totalSize += element.size;
        });
        
        this.computedPicturesListSize = totalSize;
    }

    computePicturesLength() {
        let length = 0;

        this.pictures.forEach(element => {
            if (!element.type.match("deleted")) {
                length++;
            }
        });

        this.computedPicturesLength = length;
    }

    get productName() { return this.productForm.get('productName') }
    get serialNumber() { return this.productForm.get('serialNumber') }
    get quantity() { return this.productForm.get('quantity') }
    get project() { return this.productForm.get('project') }
    get statusId() { return this.productForm.get('statusId') }
    get description() { return this.productForm.get('description') }
    get uploadPicture() { return this.productForm.get('uploadPicture') }
    get thumbArray() { return this.productForm.get('thumbArray') as FormArray }
    // get thumb() { return this.productForm.get('thumbFormGroup').get('thumb') }
    get editMode() { return this.productService.editMode }
    get MAX_UPLOAD_FILES_SIZE() { return EditProductComponent.MAX_UPLOAD_FILES_SIZE }
    get MAX_UPLOAD_FILES_LENGTH() { return EditProductComponent.MAX_UPLOAD_FILES_LENGTH }
}
