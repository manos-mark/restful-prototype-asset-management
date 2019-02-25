export interface Action {
    id: number,
    name: string,
    cssClass: string
}

export enum Actions {
    'LOGGED_IN' = 1, 
    'CREATED_PROJECT' = 2,
    'UPDATED_PROJECT' = 3,
    'DELETED_PROJECT' = 4,
    'CREATED_PRODUCT' = 5,
    'UPDATED_PRODUCT' = 6,
    'DELETED_PRODUCT' = 7,
    'LOGGED_OUT' = 8,
}
export const ActionsMap = new Map<Actions, Action>([
    [Actions.LOGGED_IN, {
        id: Actions.LOGGED_IN,
        name: 'Logged in',
        cssClass: 'user'
    }],
    [Actions.CREATED_PROJECT, {
        id: Actions.CREATED_PROJECT,
        name: 'Created new Project',
        cssClass: 'project'
    }],
    [Actions.UPDATED_PROJECT, {
        id: Actions.UPDATED_PROJECT,
        name: 'Updated a Project',
        cssClass: 'project'
    }],
    [Actions.DELETED_PROJECT, {
        id: Actions.DELETED_PROJECT,
        name: 'Deleted a Project',
        cssClass: 'project'
    }],
    [Actions.CREATED_PRODUCT, {
        id: Actions.CREATED_PRODUCT,
        name: 'Created new Product',
        cssClass: 'product'
    }],
    [Actions.UPDATED_PRODUCT, {
        id: Actions.UPDATED_PRODUCT,
        name: 'Updated a Product',
        cssClass: 'product'
    }],
    [Actions.DELETED_PRODUCT, {
        id: Actions.DELETED_PRODUCT,
        name: 'Deleted a Product',
        cssClass: 'product'
    }],
    [Actions.LOGGED_OUT, {
        id: Actions.LOGGED_OUT,
        name: 'Logged out',
        cssClass: 'user'
    }]
]);
