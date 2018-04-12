import { BaseEntity } from './../../shared';

export class Journey implements BaseEntity {
    constructor(
        public id?: number,
        public tripId?: number,
        public cityId?: number,
        public countryId?: number,
    ) {
    }
}
