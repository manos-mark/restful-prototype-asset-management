import { NgModule } from '@angular/core';
import { WindowPopComponent } from './window-pop/window-pop.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ImageCarouselComponent } from './image-carousel/image-carousel.component';
import { LightboxModule } from 'ngx-lightbox';
import { NotificationComponent } from './notification/notification.component';
import { ForgottenPasswordComponent } from '../auth/login/forgotten-password/forgotten-password.component';
import { LoaderComponent } from './loader/loader.component';


@NgModule({
    declarations: [
        WindowPopComponent,
        ImageCarouselComponent,
        NotificationComponent,
        ForgottenPasswordComponent,
        LoaderComponent
    ],
    imports: [
        FormsModule,
        ReactiveFormsModule,
        LightboxModule,
        CommonModule
    ],
    exports: [
        WindowPopComponent,
        ImageCarouselComponent,
        NotificationComponent,
        ForgottenPasswordComponent,
        LoaderComponent
    ]
})
export class SharedModule {}