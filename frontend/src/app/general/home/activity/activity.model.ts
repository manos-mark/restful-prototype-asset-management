import { Action, ActionsMap } from './action.enum';

export class Activity {
    
    public id: number;
    public action: Action;
    public date: string;

    constructor(dto:any) {
        this.id = dto.id;
        this.action = ActionsMap.get(dto.actionId);
        this.date = dto.date;
    }
}

// export class Activity {
//     constructor(public id: number,
//         public actionId: number,
//         public date: string
//     ) {}
// }