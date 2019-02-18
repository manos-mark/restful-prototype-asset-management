import { Project } from '../projects/project.model';

export class Product {
    public id: number;
    public date: string;
    public name: string;
    public serialNumber: string;
    public quantity: string;
    public description: string;
    public project: number;
    public status: number;

    constructor(dto:any) {
        this.id = dto.id;
        this.date = dto.date;
        this.name = dto.name;
        this.serialNumber = dto.serialNumber;
        this.quantity = dto.quantity;
        this.description = dto.description;
        this.project = dto.projectId;
        this.status = dto.statusId;
    } 
}