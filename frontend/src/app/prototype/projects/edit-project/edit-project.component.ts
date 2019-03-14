import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { ProjectsService } from '../projects.service';
import { Project } from '../project.model';
import { Product } from '../../products/product.model';
import { Statuses, StatusesMap } from '../../status.enum';
import { ProductsService } from '../../products/products.service';
import { PageParams } from '../page-params.model';
import { FilterParams } from '../filter-params.model';
import { WindowPopService } from 'src/app/shared/window-pop/window-pop.service';
import { ImageCarouselService } from 'src/app/shared/image-carousel/image-carousel.service';
import { ProductPicture } from '../../product-picture.model';
import { Actions } from 'src/app/general/home/activity/action.enum';
import { BreadcrumbsService } from 'src/app/shared/breadcrumbs.service';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnDestroy {
    projectManagers: {
        id: number;
        name: string;
    }[];
    project: Project;
    products: Product[] = [];
    pictures: ProductPicture[] = [];
    pageParams = new PageParams();
    filterParams = new FilterParams();


    projectForm = new FormGroup({
        projectName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        companyName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        projectManagerId: new FormControl("Choose project manager", [Validators.required, Validators.pattern("^[0-9]+$")]),
        statusId: new FormControl(null, [Validators.required, Validators.pattern("^[0-9]+$")])
    })

    constructor(private activityService: ActivityService,
                private projectService: ProjectsService,
                private router: Router,
                private route: ActivatedRoute,
                private productService: ProductsService,
                private windowPopService: WindowPopService,
                private carouselService: ImageCarouselService,
                private breadcrumbsService: BreadcrumbsService) { 
        if (this.projectService.editMode) {
            this.breadcrumbsService.breadcrumbs = [];
            this.breadcrumbsService.breadcrumbs.push({
                name: "Prototype > Projects",
                src: "prototype/projects"
            });
            this.breadcrumbsService.breadcrumbs.push({
                name: " > Edit Project",
                src: null
            });
        } 
        else {
            this.breadcrumbsService.breadcrumbs = [];
            this.breadcrumbsService.breadcrumbs.push({
                name: "Prototype > Projects",
                src: "prototype/projects"
            });
            this.breadcrumbsService.breadcrumbs.push({
                name: " > New Project",
                src: null
            });
        }
        // when add new project status always will be new and disabled
        this.projectForm.controls.statusId.setValue(Statuses.NEW);
        this.projectForm.controls.statusId.disable();
        // get project managers for the dropdown
        this.projectService.getProjectManagers()
            .subscribe(
                res => this.projectManagers = res,
                error => console.log(error)
            )
        // on edit mode init the fields        
        if (this.projectService.editMode) {
            this.route.queryParams.subscribe(
                res => {
                    this.projectService.getProjectById(res.projectId).subscribe(
                        res => {
                            this.project = new Project(res);
                            this.projectForm.controls.projectName.setValue(this.project.projectName);
                            this.projectForm.controls.companyName.setValue(this.project.companyName);
                            this.projectForm.controls.projectManagerId.setValue(this.project.projectManager.id);
                            this.projectForm.controls.statusId.enable();
                            this.projectForm.controls.statusId.setValue(this.project.status.id);
                            this.projectService.getProductsByProjectId(this.project.id)
                                .subscribe(
                                    res => {
                                        res.map(
                                            item => { this.products.push(new Product(item)) }
                                        )
                                    },
                                    error => console.log(error)
                                )
                        },
                        error => console.log(error)
                    )
                },
                error => console.log(error)
            )
        }
    }

    onOpenCarousel(productId: number) {
        this.productService.getPicturesByProductId(productId)
            .subscribe(
                res => {
                    this.pictures = [];
                    res.map( picture => {
                        this.pictures.push(new ProductPicture(picture));
                    })
                    this.carouselService.pictures = this.pictures;
                    this.carouselService.activate = true;
                },
                error => console.log(error)
            );
    }

    onAddSave() {
        // on edit mode update
        if (this.projectService.editMode) {

            let tempProject = new Object({
                projectName: this.projectName.value,
                companyName: this.companyName.value,
                projectManager: { id: this.projectManagerId.value },
                status: { id: this.statusId.value },
                id: this.project.id,
            });

            this.projectService.updateProject(tempProject)
                    .subscribe(
                        res => {
                            this.router.navigate(['/prototype/projects']);
                            this.activityService.addActivity(Actions.UPDATED_PROJECT).subscribe();
                        },
                        error => {
                            console.log(error)
                            this.windowPopService.title = "Update project Failed";
                            this.windowPopService.context = "Your request is not successful!";
                            this.windowPopService.details = "Try again with different credentials.";
                            this.windowPopService.activate = true;
                        }
                    )
        }
        // else add new project
        else {
            this.projectService.addProject(this.projectName.value, 
                this.companyName.value, this.projectManagerId.value)
                    .subscribe(
                        res => {
                            this.router.navigate(['/prototype/projects']);
                            this.activityService.addActivity(Actions.CREATED_PROJECT).subscribe();
                        },
                        error => {
                            console.log(error)
                            this.windowPopService.title = "Add new project Failed";
                            this.windowPopService.context = "Your request is not successful!";
                            this.windowPopService.details = "Try again with different credentials.";
                            this.windowPopService.activate = true;
                        }
                    )
        }
    }

    onEdit(productId: number) {
        this.productService.editMode = true;
        this.router.navigate(['prototype/products/', productId, 'edit'], 
            {queryParams: { productId: productId }}
        );
    }

    onCancel() {
        this.projectForm.reset();
        this.router.navigate(['prototype','projects']);
    }

    ngOnDestroy() {
        this.projectService.editMode = false;
    }

    get projectName() {return this.projectForm.get('projectName')}
    get companyName() {return this.projectForm.get('companyName')}
    get projectManagerId() {return this.projectForm.get('projectManagerId')}
    get statusId() {return this.projectForm.get('statusId')}
    get editMode() {return this.projectService.editMode}
}
