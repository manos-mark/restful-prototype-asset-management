export class ProductPicture {

    id: number;
    name: string;
    size: number;
    productId: number;
    file: File;

    constructor(dto) {
        this.id = dto.id,
        this.name = dto.name,
        this.size = dto.size,
        this.productId = dto.productId,
        this.file = dto.file;
    }
}