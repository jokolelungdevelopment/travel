import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FavoriteComponent } from './favorite.component';
import { FavoriteDetailComponent } from './favorite-detail.component';
import { FavoritePopupComponent } from './favorite-dialog.component';
import { FavoriteDeletePopupComponent } from './favorite-delete-dialog.component';

export const favoriteRoute: Routes = [
    {
        path: 'favorite',
        component: FavoriteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.favorite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'favorite/:id',
        component: FavoriteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.favorite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const favoritePopupRoute: Routes = [
    {
        path: 'favorite-new',
        component: FavoritePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.favorite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favorite/:id/edit',
        component: FavoritePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.favorite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favorite/:id/delete',
        component: FavoriteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.favorite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
