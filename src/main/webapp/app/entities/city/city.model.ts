import { BaseEntity } from './../../shared';

export class City implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public journeys?: BaseEntity[],
        public products?: BaseEntity[],
        public countryId?: number,
    ) {
    }
}
