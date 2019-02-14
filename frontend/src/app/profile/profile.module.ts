import { NgModule } from "@angular/core";
import { ProfileComponent } from './profile.component';
import { ReactiveFormsModule, AbstractControl } from '@angular/forms';
import { CommonModule } from '@angular/common';

@NgModule({
    declarations: [
        ProfileComponent,
    ],
    imports: [
        ReactiveFormsModule,
        CommonModule
    ]
})
export class ProfileModule {}