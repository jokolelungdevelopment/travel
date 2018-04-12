import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import {
    ProductImageService,
    ProductImagePopupService,
    ProductImageComponent,
    ProductImageDetailComponent,
    ProductImageDialogComponent,
    ProductImagePopupComponent,
    ProductImageDeletePopupComponent,
    ProductImageDeleteDialogComponent,
    productImageRoute,
    productImagePopupRoute,
} from './';

const ENTITY_STATES = [
    ...productImageRoute,
    ...productImagePopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProductImageComponent,
        ProductImageDetailComponent,
        ProductImageDialogComponent,
        ProductImageDeleteDialogComponent,
        ProductImagePopupComponent,
        ProductImageDeletePopupComponent,
    ],
    entryComponents: [
        ProductImageComponent,
        ProductImageDialogComponent,
        ProductImagePopupComponent,
        ProductImageDeleteDialogComponent,
        ProductImageDeletePopupComponent,
    ],
    providers: [
        ProductImageService,
        ProductImagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelProductImageModule {}
