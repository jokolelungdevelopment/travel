import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SlideComponent } from './slide.component';
import { SlideDetailComponent } from './slide-detail.component';
import { SlidePopupComponent } from './slide-dialog.component';
import { SlideDeletePopupComponent } from './slide-delete-dialog.component';

export const slideRoute: Routes = [
    {
        path: 'slide',
        component: SlideComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.slide.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'slide/:id',
        component: SlideDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.slide.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const slidePopupRoute: Routes = [
    {
        path: 'slide-new',
        component: SlidePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.slide.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'slide/:id/edit',
        component: SlidePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.slide.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'slide/:id/delete',
        component: SlideDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'travelApp.slide.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
