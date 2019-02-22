import { Product } from '../products/product.model';

export class Project {
    public id: number;
    public date: string;
    public projectName: string;
    public companyName: string;
    public projectManager: {
        id: number;
        name: string;
    };
    public status: number;

    constructor(dto: any) {
        this.id = dto.id;
        this.date = dto.date;
        this.projectName = dto.projectName;
        this.companyName = dto.companyName;
        this.projectManager = dto.projectManager;
        this.status = dto.statusId;
    }
}