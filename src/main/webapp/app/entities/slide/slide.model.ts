import { BaseEntity } from './../../shared';

export class Slide implements BaseEntity {
    constructor(
        public id?: number,
        public imgurl?: number,
        public url?: string,
    ) {
    }
}
