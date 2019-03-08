import { Status, StatusesMap } from '../status.enum';

export class Product {
    public id: number;
    public createdAt: Date;
    public productName: string;
    public serialNumber: string;
    public description: string;
    public quantity: string;
    public status: Status;
    public projectId: number;
    public projectName: string;
    public picturesCount: number;
    public thumbPictureId: number;
    public isChecked: boolean;
    public pictures: []; 

    constructor(dto:any) {
        this.id = dto.id;
        this.createdAt = new Date(dto.createdAt.year, dto.createdAt.monthValue, dto.createdAt.dayOfMonth);
        this.productName = dto.productName;
        this.serialNumber = dto.serialNumber;
        this.description = dto.description;
        this.quantity = dto.quantity;
        this.status = StatusesMap.get(dto.statusId);
        this.projectId = dto.projectId;
        this.projectName = dto.projectName;
        this.picturesCount = dto.picturesCount;
        this.thumbPictureId = dto.thumbPictureId;
    } 
}