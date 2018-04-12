import { BaseEntity } from './../../shared';

export class Transaction implements BaseEntity {
    constructor(
        public id?: number,
        public requestid?: number,
        public preorderid?: number,
        public deliveryto?: any,
        public qty?: number,
        public price?: number,
        public currencyId?: number,
    ) {
    }
}
