export class User {
    public id: number;
    public email: string;
    public firstName: string;
    public lastName: string;
    public password: string;
    public acceptedCookies: boolean;

    constructor(dto:any) {
        this.id = dto.id;
        this.email = dto.email;
        this.firstName = dto.firstName;
        this.lastName = dto.lastName;
        this.password = dto.password;
        this.acceptedCookies = dto.acceptedCookies;
    }
}