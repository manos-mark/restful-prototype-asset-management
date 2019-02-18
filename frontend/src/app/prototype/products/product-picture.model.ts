export class ProductPicture {
    public id: number;
    public picture: File;
    public productId: number;
    public thumb: boolean;

    constructor(dto: any) {
        this.id = dto.id;
        this.picture = dto.picture;
        this.productId = dto.productId;
        this.thumb = dto.thumb;
    }
}