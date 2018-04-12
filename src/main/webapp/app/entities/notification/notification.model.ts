import { BaseEntity } from './../../shared';

export class Notification implements BaseEntity {
    constructor(
        public id?: number,
        public text?: any,
        public notifDate?: any,
        public userId?: number,
        public requestId?: number,
    ) {
    }
}
