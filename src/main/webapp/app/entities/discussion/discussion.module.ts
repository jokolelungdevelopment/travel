import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import { TravelAdminModule } from '../../admin/admin.module';
import {
    DiscussionService,
    DiscussionPopupService,
    DiscussionComponent,
    DiscussionDetailComponent,
    DiscussionDialogComponent,
    DiscussionPopupComponent,
    DiscussionDeletePopupComponent,
    DiscussionDeleteDialogComponent,
    discussionRoute,
    discussionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...discussionRoute,
    ...discussionPopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        TravelAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DiscussionComponent,
        DiscussionDetailComponent,
        DiscussionDialogComponent,
        DiscussionDeleteDialogComponent,
        DiscussionPopupComponent,
        DiscussionDeletePopupComponent,
    ],
    entryComponents: [
        DiscussionComponent,
        DiscussionDialogComponent,
        DiscussionPopupComponent,
        DiscussionDeleteDialogComponent,
        DiscussionDeletePopupComponent,
    ],
    providers: [
        DiscussionService,
        DiscussionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelDiscussionModule {}
