import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProductPicture } from '../../product-picture.model';

@Component({
  selector: 'app-product-picture',
  templateUrl: './product-picture.component.html',
  styleUrls: ['./product-picture.component.css']
})
export class ProductPictureComponent implements OnInit {
    @Input() picture: ProductPicture;
    @Input() eventFileList;
    @Input() index: number;
    @Input() thumbFormGroup: FormGroup;
    imageSrc: string;

    constructor() { }

    ngOnInit() {
        // preview images
        if (this.eventFileList && this.eventFileList[0]) {
            const file = this.eventFileList[0];
    
            const reader = new FileReader();
            reader.onload = e => this.imageSrc = reader.result.toString();
            reader.readAsDataURL(file);
        }
    }

    convertBytesToMegabytes(bytes,decimals) {
        if(bytes == 0) return '0 Bytes';
        var k = 1024, dm = decimals <= 0 ? 0 : decimals || 2,
        
        sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
        
        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }

}
