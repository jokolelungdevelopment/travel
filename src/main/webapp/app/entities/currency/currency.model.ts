import { BaseEntity } from './../../shared';

export class Currency implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public value?: string,
        public products?: BaseEntity[],
        public transactions?: BaseEntity[],
    ) {
    }
}
