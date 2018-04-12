import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import {
    SlideService,
    SlidePopupService,
    SlideComponent,
    SlideDetailComponent,
    SlideDialogComponent,
    SlidePopupComponent,
    SlideDeletePopupComponent,
    SlideDeleteDialogComponent,
    slideRoute,
    slidePopupRoute,
} from './';

const ENTITY_STATES = [
    ...slideRoute,
    ...slidePopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SlideComponent,
        SlideDetailComponent,
        SlideDialogComponent,
        SlideDeleteDialogComponent,
        SlidePopupComponent,
        SlideDeletePopupComponent,
    ],
    entryComponents: [
        SlideComponent,
        SlideDialogComponent,
        SlidePopupComponent,
        SlideDeleteDialogComponent,
        SlideDeletePopupComponent,
    ],
    providers: [
        SlideService,
        SlidePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelSlideModule {}
