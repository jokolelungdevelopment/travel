import { BaseEntity } from './../../shared';

export class ProductImage implements BaseEntity {
    constructor(
        public id?: number,
        public imgUrl?: string,
        public productId?: number,
    ) {
    }
}
