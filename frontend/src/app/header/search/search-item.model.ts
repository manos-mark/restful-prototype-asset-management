import { SearchProject } from './search-project.model';
import { SearchProduct } from './search-product.model';

export class SearchItem {

    private _project: SearchProject;
    public get project(): SearchProject {
        return this._project;
    }
    public set project(v: SearchProject) {
        this._project = v;
    }

    private _product: SearchProduct;
    public get product(): SearchProduct {
        return this._product;
    }
    public set product(v: SearchProduct) {
        this._product = v;
    }

    private _isHovered: Boolean;
    public get isHovered(): Boolean {
        return this._isHovered;
    }
    public set isHovered(v: Boolean) {
        this._isHovered = v;
    }

    private _isSelected: Boolean;
    public get isSelected(): Boolean {
        return this._isSelected;
    }
    public set isSelected(v: Boolean) {
        this._isSelected = v;
    }


    private _cssClass: string;
    public get cssClass(): string {
        return this._cssClass;
    }
    public set cssClass(v: string) {
        this._cssClass = v;
    }

    private _type: string;
    public get type(): string {
        return this._type;
    }
    public set type(v: string) {
        this._type = v;
    }

    private _item: string;
    public get item(): string {
        return this._item;
    }
    public set item(v: string) {
        this._item = v;
    }

    constructor(from) {
        if (from.productName) {
            this.product = from;
            if (this.product.field.match('productName')) {
                this._cssClass = 'product';
                this.type = 'Product name';
                this.item = this.product.productName;
            } else if (this.product.field.match('serialNumber')) {
                this._cssClass = 'serialNum';
                this.type = 'Serial number';
                this.item = this.product.serialNumber;
            }
        } else if (from.projectName) {
            this.project = from;
            if (this.project.field.match('projectName')) {
                this._cssClass = 'project';
                this.type = 'Project name';
                this.item = this.project.projectName;
            } else if (this.project.field.match('companyName')) {
                this._cssClass = 'company';
                this.type = 'Company';
                this.item = this.project.companyName;
            } else if (this.project.field.match('projectManager.name')) {
                this._cssClass = 'manager';
                this.type = 'Project Manager';
                this.item = this.project.projectManagerName;
            }
        }
    }
}
