import { NgModule } from '@angular/core';
import { WindowPopComponent } from './window-pop/window-pop.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@NgModule({
    declarations: [
        WindowPopComponent
    ],
    imports: [
        FormsModule,
        ReactiveFormsModule,
        CommonModule
    ],
    exports: [
        WindowPopComponent
    ]
})
export class SharedModule {}