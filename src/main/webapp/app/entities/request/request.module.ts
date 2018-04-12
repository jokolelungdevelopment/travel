import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import { TravelAdminModule } from '../../admin/admin.module';
import {
    RequestService,
    RequestPopupService,
    RequestComponent,
    RequestDetailComponent,
    RequestDialogComponent,
    RequestPopupComponent,
    RequestDeletePopupComponent,
    RequestDeleteDialogComponent,
    requestRoute,
    requestPopupRoute,
} from './';

const ENTITY_STATES = [
    ...requestRoute,
    ...requestPopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        TravelAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RequestComponent,
        RequestDetailComponent,
        RequestDialogComponent,
        RequestDeleteDialogComponent,
        RequestPopupComponent,
        RequestDeletePopupComponent,
    ],
    entryComponents: [
        RequestComponent,
        RequestDialogComponent,
        RequestPopupComponent,
        RequestDeleteDialogComponent,
        RequestDeletePopupComponent,
    ],
    providers: [
        RequestService,
        RequestPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelRequestModule {}
