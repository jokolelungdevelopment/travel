import { BaseEntity } from './../../shared';

export class Favorite implements BaseEntity {
    constructor(
        public id?: number,
        public requestid?: number,
        public preorderid?: number,
        public userId?: number,
    ) {
    }
}
