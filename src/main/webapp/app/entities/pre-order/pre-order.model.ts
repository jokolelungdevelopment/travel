import { BaseEntity } from './../../shared';

export const enum Status {
    'OPEN',
    'REQUSTED',
    'OFFERED',
    'ACCEPTED',
    'PAID',
    'COMPLETED',
    'CLOSE',
    'COMPLAINED'
}

export class PreOrder implements BaseEntity {
    constructor(
        public id?: number,
        public orderDate?: any,
        public status?: Status,
        public userId?: number,
        public productId?: number,
        public tripId?: number,
    ) {
    }
}
