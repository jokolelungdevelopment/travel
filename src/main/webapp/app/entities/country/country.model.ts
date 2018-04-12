import { BaseEntity } from './../../shared';

export class Country implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public cities?: BaseEntity[],
        public journeys?: BaseEntity[],
        public products?: BaseEntity[],
    ) {
    }
}
