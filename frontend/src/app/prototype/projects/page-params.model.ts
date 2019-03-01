export class PageParams {

    field: string;
    page: number;
    pageSize: number;
    direction: string;

    constructor() {
        this.field = 'date';
        this.page = 1;
        this.pageSize = 5;
        this.direction = 'asc';
    }
}