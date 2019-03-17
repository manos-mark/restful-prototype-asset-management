export class SearchProject {
    
    private _id : number;
    public get id() : number {
        return this._id;
    }
    public set id(v : number) {
        this._id = v;
    }
    
    
    private _projectName : string;
    public get projectName() : string {
        return this._projectName;
    }
    public set projectName(v : string) {
        this._projectName = v;
    }
    
    
    private _companyName : string;
    public get companyName() : string {
        return this._companyName;
    }
    public set companyName(v : string) {
        this._companyName = v;
    }
    
    
    private _projectManagerName : string;
    public get projectManagerName() : string {
        return this._projectManagerName;
    }
    public set projectManagerName(v : string) {
        this._projectManagerName = v;
    }

    
    private _field : string;
    public get field() : string {
        return this._field;
    }
    public set field(v : string) {
        this._field = v;
    }
    
}