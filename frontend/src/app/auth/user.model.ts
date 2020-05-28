export class User {
    private _id : number;
    public get id() : number {
        return this._id;
    }
    public set id(v : number) {
        this._id = v;
    }
    
    private _email : string;
    public get email() : string {
        return this._email;
    }
    public set email(v : string) {
        this._email = v;
    }
    
    private _firstName : string;
    public get firstName() : string {
        return this._firstName;
    }
    public set firstName(v : string) {
        this._firstName = v;
    }
    
    private _lastName : string;
    public get lastName() : string {
        return this._lastName;
    }
    public set lastName(v : string) {
        this._lastName = v;
    }
    
    private _acceptedCookies : boolean;
    public get acceptedCookies() : boolean {
        return this._acceptedCookies;
    }
    public set acceptedCookies(v : boolean) {
        this._acceptedCookies = v;
    }
    
    private _password : string;
    public get password() : string {
        return this._password;
    }
    public set password(v : string) {
        this._password = v;
    }
    

    constructor(dto:any) {
        this.id = dto.id;
        this.email = dto.email;
        this.firstName = dto.firstName;
        this.lastName = dto.lastName;
        this.password = dto.password;
        this.acceptedCookies = dto.acceptedCookies;
    }
}