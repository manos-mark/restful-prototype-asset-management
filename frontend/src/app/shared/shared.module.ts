import { NgModule } from '@angular/core';
import { WindowPopComponent } from './window-pop/window-pop.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ImageCarouselComponent } from './image-carousel/image-carousel.component';
import { LightboxModule } from 'ngx-lightbox';


@NgModule({
    declarations: [
        WindowPopComponent,
        ImageCarouselComponent
    ],
    imports: [
        FormsModule,
        ReactiveFormsModule,
        LightboxModule,
        CommonModule
    ],
    exports: [
        WindowPopComponent,
        ImageCarouselComponent
    ]
})
export class SharedModule {}