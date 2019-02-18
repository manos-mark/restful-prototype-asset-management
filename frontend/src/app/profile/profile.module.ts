import { NgModule } from "@angular/core";
import { ProfileComponent } from './profile.component';
import { ReactiveFormsModule, AbstractControl, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';

@NgModule({
    declarations: [
        ProfileComponent
    ],
    imports: [
        FormsModule,
        ReactiveFormsModule,
        SharedModule,
        CommonModule
    ]
})
export class ProfileModule {}