import { NgModule } from '@angular/core';
import { DashboardComponent } from './home/dashboard/dashboard.component';
import { ActivityComponent } from './home/activity/activity.component';
import { GeneralComponent } from './general.component';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';

@NgModule({
    declarations: [
        DashboardComponent,
        ActivityComponent,
        GeneralComponent,
        HomeComponent
    ],
    imports: [
        RouterModule
    ]
})
export class GeneralModule {}
