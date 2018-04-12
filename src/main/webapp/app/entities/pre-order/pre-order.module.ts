import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import { TravelAdminModule } from '../../admin/admin.module';
import {
    PreOrderService,
    PreOrderPopupService,
    PreOrderComponent,
    PreOrderDetailComponent,
    PreOrderDialogComponent,
    PreOrderPopupComponent,
    PreOrderDeletePopupComponent,
    PreOrderDeleteDialogComponent,
    preOrderRoute,
    preOrderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...preOrderRoute,
    ...preOrderPopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        TravelAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PreOrderComponent,
        PreOrderDetailComponent,
        PreOrderDialogComponent,
        PreOrderDeleteDialogComponent,
        PreOrderPopupComponent,
        PreOrderDeletePopupComponent,
    ],
    entryComponents: [
        PreOrderComponent,
        PreOrderDialogComponent,
        PreOrderPopupComponent,
        PreOrderDeleteDialogComponent,
        PreOrderDeletePopupComponent,
    ],
    providers: [
        PreOrderService,
        PreOrderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelPreOrderModule {}
