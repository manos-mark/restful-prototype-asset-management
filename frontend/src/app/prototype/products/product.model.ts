
export class Product {
    public id: number;
    public date: string;
    public name: string;
    public serialNumber: string;
    public quantity: string;
    public description: string;
    public projectId: number;
    public statusId: number;

    constructor(dto:any) {
        this.id = dto.id;
        this.date = dto.date;
        this.name = dto.name;
        this.serialNumber = dto.serialNumber;
        this.quantity = dto.quantity;
        this.description = dto.description;
        this.projectId = dto.projectId;
        this.statusId = dto.statusId;
    } 
}