import { BaseEntity } from './../../shared';

export const enum StatusUser {
    'NOT',
    'VERIFIED'
}

export class UserInfo implements BaseEntity {
    constructor(
        public id?: number,
        public fullname?: string,
        public birthdate?: string,
        public phoneNumber?: string,
        public status?: StatusUser,
        public balance?: number,
        public imgurl?: string,
        public gmailToken?: any,
        public facebookToken?: any,
        public userId?: number,
    ) {
    }
}
