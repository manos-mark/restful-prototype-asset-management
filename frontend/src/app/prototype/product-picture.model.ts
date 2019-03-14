export class ProductPicture {

    id: number;
    name: string;
    size: number;
    productId: number;
    file: File;
    imageSrc: string = "";
    type: string;
    isThumb: Boolean;

    constructor(dto) {
        this.id = dto.id,
        this.name = dto.name,
        this.size = dto.size,
        this.productId = dto.productId,
        this.file = dto.file,
        this.isThumb = dto.isThumb
        

        if (this.id) {
            this.type = "existing";
            this.imageSrc = 'api/product-pictures/' + this.id;
        } else {
            this.type = "new";
            const reader = new FileReader();
            reader.onload = e => this.imageSrc = reader.result.toString();
            reader.readAsDataURL(this.file);
        }
    }
}