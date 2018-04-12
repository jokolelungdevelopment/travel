import { BaseEntity } from './../../shared';

export class Inbox implements BaseEntity {
    constructor(
        public id?: number,
        public subject?: any,
        public postDate?: any,
        public messages?: BaseEntity[],
        public senderId?: number,
        public receiverId?: number,
    ) {
    }
}
