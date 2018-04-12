import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import { TravelAdminModule } from '../../admin/admin.module';
import {
    NotificationService,
    NotificationPopupService,
    NotificationComponent,
    NotificationDetailComponent,
    NotificationDialogComponent,
    NotificationPopupComponent,
    NotificationDeletePopupComponent,
    NotificationDeleteDialogComponent,
    notificationRoute,
    notificationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...notificationRoute,
    ...notificationPopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        TravelAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NotificationComponent,
        NotificationDetailComponent,
        NotificationDialogComponent,
        NotificationDeleteDialogComponent,
        NotificationPopupComponent,
        NotificationDeletePopupComponent,
    ],
    entryComponents: [
        NotificationComponent,
        NotificationDialogComponent,
        NotificationPopupComponent,
        NotificationDeleteDialogComponent,
        NotificationDeletePopupComponent,
    ],
    providers: [
        NotificationService,
        NotificationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelNotificationModule {}
