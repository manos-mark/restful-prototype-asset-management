export interface Status {
    id: number;
    name: string;
    cssClass: string;
}

export enum Statuses {
    'IN_PROGRESS' = 1,
    'NEW' = 2,
    'FINISHED' = 3
}
export const StatusesMap = new Map<Statuses, Status>([
    [Statuses.IN_PROGRESS, {
        id: Statuses.IN_PROGRESS,
        name: 'In Progress',
        cssClass: 'orange'
    }],
    [Statuses.NEW, {
        id: Statuses.NEW,
        name: 'New',
        cssClass: 'gray'
    }],
    [Statuses.FINISHED, {
        id: Statuses.FINISHED,
        name: 'Finished',
        cssClass: 'green'
    }]
]);
