import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TravelSharedModule } from '../../shared';
import { TravelAdminModule } from '../../admin/admin.module';
import {
    FavoriteService,
    FavoritePopupService,
    FavoriteComponent,
    FavoriteDetailComponent,
    FavoriteDialogComponent,
    FavoritePopupComponent,
    FavoriteDeletePopupComponent,
    FavoriteDeleteDialogComponent,
    favoriteRoute,
    favoritePopupRoute,
} from './';

const ENTITY_STATES = [
    ...favoriteRoute,
    ...favoritePopupRoute,
];

@NgModule({
    imports: [
        TravelSharedModule,
        TravelAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FavoriteComponent,
        FavoriteDetailComponent,
        FavoriteDialogComponent,
        FavoriteDeleteDialogComponent,
        FavoritePopupComponent,
        FavoriteDeletePopupComponent,
    ],
    entryComponents: [
        FavoriteComponent,
        FavoriteDialogComponent,
        FavoritePopupComponent,
        FavoriteDeleteDialogComponent,
        FavoriteDeletePopupComponent,
    ],
    providers: [
        FavoriteService,
        FavoritePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TravelFavoriteModule {}
