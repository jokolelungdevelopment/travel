import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JourneyComponent } from './journey.component';
import { JourneyDetailComponent } from './journey-detail.component';
import { JourneyPopupComponent } from './journey-dialog.component';
import { JourneyDeletePopupComponent } from './journey-delete-dialog.component';

export const journeyRoute: Routes = [
    {
        path: 'journey',
        component: JourneyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.journey.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'journey/:id',
        component: JourneyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.journey.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const journeyPopupRoute: Routes = [
    {
        path: 'journey-new',
        component: JourneyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.journey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'journey/:id/edit',
        component: JourneyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.journey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'journey/:id/delete',
        component: JourneyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.journey.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
