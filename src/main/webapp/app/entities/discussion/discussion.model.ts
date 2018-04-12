import { BaseEntity } from './../../shared';

export class Discussion implements BaseEntity {
    constructor(
        public id?: number,
        public text?: any,
        public postDate?: any,
        public requestid?: number,
        public preorderid?: number,
        public userId?: number,
    ) {
    }
}
