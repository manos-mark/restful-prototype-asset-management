import { Product } from 'src/app/prototype/products/product.model';
import { Project } from 'src/app/prototype/projects/project.model';

export class Search {

    
    private _products : Product[];
    public get products() : Product[] {
        return this._products;
    }
    public set products(v : Product[]) {
        this._products = v;
    }
    
    private _projects : Project[];
    public get projects() : Project[] {
        return this._projects;
    }
    public set projects(v : Project[]) {
        this._projects = v;
    }
    
    constructor(dto: any) {
        this._products = dto.products;
        this.projects = dto.projects;
    }
}