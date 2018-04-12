import { BaseEntity } from './../../shared';

export const enum StatusOffer {
    'OFFERED',
    'CANCEL',
    'ACCEPTED'
}

export class Offer implements BaseEntity {
    constructor(
        public id?: number,
        public offerDate?: any,
        public price?: number,
        public note?: string,
        public status?: StatusOffer,
        public userId?: number,
        public requestId?: number,
        public tripId?: number,
    ) {
    }
}
