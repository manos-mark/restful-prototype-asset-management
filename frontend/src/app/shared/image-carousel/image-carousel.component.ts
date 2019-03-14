import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ImageCarouselService } from './image-carousel.service';
import { Lightbox, LightboxEvent, LIGHTBOX_EVENT } from 'ngx-lightbox';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-image-carousel',
  templateUrl: './image-carousel.component.html',
  styleUrls: ['./image-carousel.component.css']
})
export class ImageCarouselComponent {
    @Input() active: Boolean = false;
    @Output() activeChange = new EventEmitter<Boolean>();
    // pictures = [1, 2, 3].map(() => `https://picsum.photos/900/500?random&t=${Math.random()}`);
    private _subscription: Subscription;
    albums = [];

    constructor(private lightbox: Lightbox, 
                private carouselService: ImageCarouselService,
                private _lightboxEvent: LightboxEvent) {}

    private _onReceivedEvent(event: any): void {
        // remember to unsubscribe the event when lightbox is closed
        if (event.id === LIGHTBOX_EVENT.CLOSE) {
            // event CLOSED is fired
            this._subscription.unsubscribe();
            this.lightbox.close();
            this.activeChange.emit(false);
        }

        if (event.id === LIGHTBOX_EVENT.OPEN) {
            // event OPEN is fired
        }

        if (event.id === LIGHTBOX_EVENT.CHANGE_PAGE) {
            // event change page is fired
            console.log(event.data); // -> image index that lightbox is switched to
        }
    }

    ngOnChanges() {
        if (this.active) {
            // init album
            this.albums = [];
            for (let picture of this.carouselService.pictures) {
                const album = {
                    src: picture.imageSrc,
                    caption: picture.name,
                    thumb: picture.imageSrc
                };
                this.albums.push(album);
            }
            this.lightbox.open(this.albums, 0);
            // register your subscription and callback whe open lightbox is fired
            this._subscription = this._lightboxEvent.lightboxEvent$
                .subscribe(event => this._onReceivedEvent(event));
        }
    }
}
