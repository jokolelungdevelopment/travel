import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import {
    JourneyService,
    JourneyPopupService,
    JourneyComponent,
    JourneyDetailComponent,
    JourneyDialogComponent,
    JourneyPopupComponent,
    JourneyDeletePopupComponent,
    JourneyDeleteDialogComponent,
    journeyRoute,
    journeyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...journeyRoute,
    ...journeyPopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        JourneyComponent,
        JourneyDetailComponent,
        JourneyDialogComponent,
        JourneyDeleteDialogComponent,
        JourneyPopupComponent,
        JourneyDeletePopupComponent,
    ],
    entryComponents: [
        JourneyComponent,
        JourneyDialogComponent,
        JourneyPopupComponent,
        JourneyDeleteDialogComponent,
        JourneyDeletePopupComponent,
    ],
    providers: [
        JourneyService,
        JourneyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelJourneyModule {}
