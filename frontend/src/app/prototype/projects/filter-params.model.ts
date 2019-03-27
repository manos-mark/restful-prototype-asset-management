export class FilterParams {

    private _fromDate: Date;
    public get fromDate(): Date {
        return this._fromDate;
    }
    public set fromDate(v: Date) {
        this._fromDate = v;
    }

    private _toDate: Date;
    public get toDate(): Date {
        return this._toDate;
    }
    public set toDate(v: Date) {
        this._toDate = v;
    }

    private _statusId: number;
    public get statusId(): number {
        return this._statusId;
    }
    public set statusId(v: number) {
        this._statusId = v;
    }

    private _projectName: string;
    public get projectName(): string {
        return this._projectName;
    }
    public set projectName(v: string) {
        this._projectName = v;
    }
    constructor() {
        this.projectName = '';
    }

    isUndefined(): boolean {
        return this.fromDate === undefined && this.toDate === undefined && this.statusId === undefined && this.projectName === '';
    }
}
