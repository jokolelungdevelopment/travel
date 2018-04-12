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

export class Request implements BaseEntity {
    constructor(
        public id?: number,
        public requestDate?: any,
        public viewed?: number,
        public status?: Status,
        public travelerid?: number,
        public offers?: BaseEntity[],
        public notifications?: BaseEntity[],
        public userId?: number,
        public productId?: number,
    ) {
    }
}
