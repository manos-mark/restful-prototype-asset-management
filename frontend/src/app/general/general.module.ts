import { NgModule } from '@angular/core';
import { DashboardComponent } from './home/dashboard/dashboard.component';
import { ActivityComponent } from './home/activity/activity.component';
import { GeneralComponent } from './general.component';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';

@NgModule({
    declarations: [
        DashboardComponent,
        ActivityComponent,
        GeneralComponent,
        HomeComponent
    ],
    imports: [
        RouterModule,
        CommonModule,
        SharedModule
    ]
})
export class GeneralModule {}
