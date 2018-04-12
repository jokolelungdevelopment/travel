import { BaseEntity } from './../../shared';

export const enum Size {
    'SMALL',
    'MEDIUM',
    'BIG'
}

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public productname?: string,
        public description?: any,
        public brand?: string,
        public url?: string,
        public quantity?: string,
        public spesialtreatment?: boolean,
        public fragile?: boolean,
        public productweight?: number,
        public productsize?: Size,
        public productprice?: number,
        public pruducttip?: number,
        public additionalcharge?: number,
        public total?: number,
        public productImages?: BaseEntity[],
        public requests?: BaseEntity[],
        public preOrders?: BaseEntity[],
        public userId?: number,
        public categoryId?: number,
        public cityId?: number,
        public countryId?: number,
        public currencyId?: number,
    ) {
        this.spesialtreatment = false;
        this.fragile = false;
    }
}
