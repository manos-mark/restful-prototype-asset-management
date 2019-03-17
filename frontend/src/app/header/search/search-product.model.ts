export class SearchProduct {
    
    private _id : number;
    public get id() : number {
        return this._id;
    }
    public set id(v : number) {
        this._id = v;
    }
    
    
    private _productName : string;
    public get productName() : string {
        return this._productName;
    }
    public set productName(v : string) {
        this._productName = v;
    }
    
    
    private _serialNumber : string;
    public get serialNumber() : string {
        return this._serialNumber;
    }
    public set serialNumber(v : string) {
        this._serialNumber = v;
    }
    
    private _field : string;
    public get field() : string {
        return this._field;
    }
    public set field(v : string) {
        this._field = v;
    }
    
}