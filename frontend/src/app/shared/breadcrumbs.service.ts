class Breadcrumb {
    name: string;
    src: string;
}
export class BreadcrumbsService extends Breadcrumb{

    
    private _breadcrumbs : Breadcrumb[];
    public get breadcrumbs() : Breadcrumb[] {
        return this._breadcrumbs;
    }
    // public set breadcrumbs(v : Breadcrumb[]) {
    //     this._breadcrumbs = v;
    // }

    setBreadcrumbsGeneral() {
        this._breadcrumbs = [];
        this._breadcrumbs.push({
            name: "General",
            src: "general"
        });
    }   
    
    setBreadcrumbsProducts() {
        this._breadcrumbs = [];
        this._breadcrumbs.push({
            name: "Prototype > Products",
            src: "prototype/products"
        });
    }

    setBreadcrumbsProductNew() {
        this._breadcrumbs = [];
        this._breadcrumbs.push({
            name: "Prototype > Products ",
            src: "prototype/products"
        });
        this._breadcrumbs.push({
            name: " > New Product",
            src: null
        });
    }

    setBreadcrumbsProductEdit() {
        this._breadcrumbs = [];
        this._breadcrumbs.push({
            name: "Prototype > Products ",
            src: "prototype/products"
        });
        this._breadcrumbs.push({
            name: " > Edit Product",
            src: null
        });
    }

    setBreadcrumbsProjects() {
        this._breadcrumbs = [];
        this._breadcrumbs.push({
            name: "Prototype > Projects",
            src: "prototype/projects"
        });
    }

    setBreadcrumbsProjectNew() {
        this._breadcrumbs = [];
        this._breadcrumbs.push({
            name: "Prototype > Projects",
            src: "prototype/projects"
        });
        this._breadcrumbs.push({
            name: " > New Project",
            src: null
        });
    }

    setBreadcrumbsProjectEdit() {
        this._breadcrumbs = [];
        this._breadcrumbs.push({
            name: "Prototype > Projects",
            src: "prototype/projects"
        });
        this._breadcrumbs.push({
            name: " > Edit Project",
            src: null
        });
    }

    setBreadcrumbsProfile() {
        this._breadcrumbs = [];
        this._breadcrumbs.push({
            name: "Settings",
            src: "profile"
        });
    }
}
