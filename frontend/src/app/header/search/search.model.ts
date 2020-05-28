import { SearchProduct } from './search-product.model';
import { SearchProject } from './search-project.model';

export class Search {

    
    private _products : SearchProduct[];
    public get products() : SearchProduct[] {
        return this._products;
    }
    public set products(v : SearchProduct[]) {
        this._products = v;
    }
    
    private _projects : SearchProject[];
    public get projects() : SearchProject[] {
        return this._projects;
    }
    public set projects(v : SearchProject[]) {
        this._projects = v;
    }
    
    constructor(dto: any) {
        this._products = dto.products;
        this.projects = dto.projects;
    }
}