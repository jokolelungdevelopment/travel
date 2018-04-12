import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import { TravelAdminModule } from '../../admin/admin.module';
import {
    UserInfoService,
    UserInfoPopupService,
    UserInfoComponent,
    UserInfoDetailComponent,
    UserInfoDialogComponent,
    UserInfoPopupComponent,
    UserInfoDeletePopupComponent,
    UserInfoDeleteDialogComponent,
    userInfoRoute,
    userInfoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userInfoRoute,
    ...userInfoPopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        TravelAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserInfoComponent,
        UserInfoDetailComponent,
        UserInfoDialogComponent,
        UserInfoDeleteDialogComponent,
        UserInfoPopupComponent,
        UserInfoDeletePopupComponent,
    ],
    entryComponents: [
        UserInfoComponent,
        UserInfoDialogComponent,
        UserInfoPopupComponent,
        UserInfoDeleteDialogComponent,
        UserInfoDeletePopupComponent,
    ],
    providers: [
        UserInfoService,
        UserInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelUserInfoModule {}
