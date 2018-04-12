import { BaseEntity } from './../../shared';

export class Trip implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public journeys?: BaseEntity[],
        public offers?: BaseEntity[],
        public preOrders?: BaseEntity[],
        public userId?: number,
    ) {
    }
}
