import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import { TravelAdminModule } from '../../admin/admin.module';
import {
    MessagesService,
    MessagesPopupService,
    MessagesComponent,
    MessagesDetailComponent,
    MessagesDialogComponent,
    MessagesPopupComponent,
    MessagesDeletePopupComponent,
    MessagesDeleteDialogComponent,
    messagesRoute,
    messagesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...messagesRoute,
    ...messagesPopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        TravelAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MessagesComponent,
        MessagesDetailComponent,
        MessagesDialogComponent,
        MessagesDeleteDialogComponent,
        MessagesPopupComponent,
        MessagesDeletePopupComponent,
    ],
    entryComponents: [
        MessagesComponent,
        MessagesDialogComponent,
        MessagesPopupComponent,
        MessagesDeleteDialogComponent,
        MessagesDeletePopupComponent,
    ],
    providers: [
        MessagesService,
        MessagesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelMessagesModule {}
