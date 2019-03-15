import { ProductPicture } from 'src/app/prototype/product-picture.model';

export class ImageCarouselService {
    
    private _activate : boolean;
    public get activate() : boolean {
        return this._activate;
    }
    public set activate(v : boolean) {
        this._activate = v;
    }
    
    
    private _pictures : ProductPicture[];
    public get pictures() : ProductPicture[] {
        return this._pictures;
    }
    public set pictures(v : ProductPicture[]) {
        this._pictures = v;
    }
}