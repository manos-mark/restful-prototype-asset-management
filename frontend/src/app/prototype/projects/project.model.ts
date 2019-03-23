import { StatusesMap, Status } from '../status.enum';

export class Project {
    public id: number;
    public createdAt: Date;
    public projectName: string;
    public companyName: string;
    public projectManager: {
        id: number;
        name: string;
    };
    public status: Status;
    public productsCount: number;
    public isChecked: boolean;

    constructor(dto: any) {
        this.id = dto.id;
        this.createdAt = new Date(dto.createdAt.year, dto.createdAt.monthValue-1, dto.createdAt.dayOfMonth);
        this.projectName = dto.projectName;
        this.companyName = dto.companyName;
        this.projectManager = dto.projectManager;
        this.status = StatusesMap.get(dto.statusId);
        this.productsCount = dto.productsCount;
    }
}