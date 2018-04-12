import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import { TravelAdminModule } from '../../admin/admin.module';
import {
    InboxService,
    InboxPopupService,
    InboxComponent,
    InboxDetailComponent,
    InboxDialogComponent,
    InboxPopupComponent,
    InboxDeletePopupComponent,
    InboxDeleteDialogComponent,
    inboxRoute,
    inboxPopupRoute,
} from './';

const ENTITY_STATES = [
    ...inboxRoute,
    ...inboxPopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        TravelAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InboxComponent,
        InboxDetailComponent,
        InboxDialogComponent,
        InboxDeleteDialogComponent,
        InboxPopupComponent,
        InboxDeletePopupComponent,
    ],
    entryComponents: [
        InboxComponent,
        InboxDialogComponent,
        InboxPopupComponent,
        InboxDeleteDialogComponent,
        InboxDeletePopupComponent,
    ],
    providers: [
        InboxService,
        InboxPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelInboxModule {}
