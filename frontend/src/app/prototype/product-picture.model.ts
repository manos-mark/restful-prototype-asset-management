export class ProductPicture {

    id: number;
    name: string;
    size: number;
    productId: number;
    file: File;
    imageSrc: string = "";

    constructor(dto) {
        this.id = dto.id,
        this.name = dto.name,
        this.size = dto.size,
        this.productId = dto.productId,
        this.file = dto.file;

        if (this.id) {
            this.imageSrc = 'api/product-pictures/' + this.id;
        } else {
            const reader = new FileReader();
            reader.onload = e => this.imageSrc = reader.result.toString();
            reader.readAsDataURL(this.file);
        }
    }
}