import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ImageCarouselService } from './image-carousel.service';
import { Lightbox } from 'ngx-lightbox';

@Component({
  selector: 'app-image-carousel',
  templateUrl: './image-carousel.component.html',
  styleUrls: ['./image-carousel.component.css']
})
export class ImageCarouselComponent  {
    @Input() active: Boolean;
    @Output() activeChange = new EventEmitter<string>();
    pictures = [1, 2, 3].map(() => `https://picsum.photos/900/500?random&t=${Math.random()}`);

    albums = [];

    constructor(private lightbox: Lightbox, private carouselService: ImageCarouselService) {
        for (let picture of this.pictures) {
            const src = this.pictures;
            const caption = 'Image ' + ' caption here';
            const thumb = picture;
            const album = {
                src: src,
                caption: caption,
                thumb: thumb
            };
        
            this.albums.push(album);
        }
    }
 
    open(index: number): void {
        // open lightbox
        this.lightbox.open(this.albums, index);
        console.log('open')
    }
    
    close(): void {
        // close lightbox programmatically
        this.lightbox.close();
    }
    onCancel() {
        this.activeChange.emit(); 
    }
    
    // get pictures() { return this.carouselService.pictures }
}
