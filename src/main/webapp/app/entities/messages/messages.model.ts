import { BaseEntity } from './../../shared';

export class Messages implements BaseEntity {
    constructor(
        public id?: number,
        public text?: any,
        public postDate?: any,
        public userId?: number,
        public inboxId?: number,
    ) {
    }
}
