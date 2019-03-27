import { Action, ActionsMap } from './action.enum';

export class Activity {

    public id: number;
    public action: Action;
    public date: Date;

    constructor(dto: any) {
        this.id = dto.id;
        this.action = ActionsMap.get(dto.actionId);
        this.date = new Date(dto.date.year, dto.date.monthValue - 1, dto.date.dayOfMonth, dto.date.hour, dto.date.minute, dto.date.second);
    }
}
